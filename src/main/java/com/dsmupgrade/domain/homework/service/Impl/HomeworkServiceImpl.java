package com.dsmupgrade.domain.homework.service.Impl;

import com.dsmupgrade.domain.homework.domain.*;
import com.dsmupgrade.domain.homework.dto.request.HomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.UserRequest;
import com.dsmupgrade.domain.homework.dto.response.HomeworkAdminResponse;
import com.dsmupgrade.domain.homework.dto.response.HomeworkContentAdminResponse;
import com.dsmupgrade.domain.homework.dto.response.HomeworkContentResponse;
import com.dsmupgrade.domain.homework.dto.response.HomeworkListResponse;
import com.dsmupgrade.domain.homework.service.CheckHomeworkService;
import com.dsmupgrade.domain.homework.service.HomeworkService;
import com.dsmupgrade.domain.homework.service.S3HomeworkFileUploader;
import com.dsmupgrade.domain.homework.service.UsersRetrieveService;
import com.dsmupgrade.domain.student.domain.Student;
import com.dsmupgrade.domain.student.domain.StudentRepository;
import com.dsmupgrade.global.error.exception.HomeworkNotFoundException;
import com.dsmupgrade.global.error.exception.InvalidInputValueException;
import com.dsmupgrade.global.error.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeworkServiceImpl implements HomeworkService {
    private final PersonalHomeworkRepository personalHomeworkRepository;
    private final HomeworkRepository homeworkRepository;
    private final StudentRepository studentRepository;
    private final S3HomeworkFileUploader fileUploader;
    private final HomeworkFileRepository homeworkFileRepository;

    private final UsersRetrieveService usersRetrieveService;
    private final CheckHomeworkService checkHomeworkService;

    @Override
    public List<HomeworkListResponse> getHomeworkList(String username) {
        checkHomeworkService.checkTimeOut(username);
        return personalHomeworkRepository.findByIdStudentUsername(username)
                .stream().map(HomeworkListResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public HomeworkContentResponse getHomeworkContent(String username, int id) {
        return HomeworkContentResponse.from(
                personalHomeworkRepository.findById(new PersonalHomeworkPk(id, username))
                        .orElseThrow(() -> new HomeworkNotFoundException(id, username))
        );
    }

    @Override
    public List<HomeworkAdminResponse> getHomeworkAllList(){
        return personalHomeworkRepository.findAll()
                .stream().map(HomeworkAdminResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<HomeworkContentAdminResponse> getHomeworkContentAdmin(String username, int id) {
        return personalHomeworkRepository.findById(new PersonalHomeworkPk(id, username))
                .stream().map(HomeworkContentAdminResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void assignmentHomework(HomeworkRequest request) {

        if (!studentRepository.existsByUsernameIn(request.getUsernames())) {
            throw new StudentNotFoundException();
        }

        Homework homework = Homework.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .deadline(request.getDeadline())
                .build();
        homeworkRepository.save(homework);

        request.getUsernames()
                .forEach((username) -> {
                    Student student = studentRepository.findByUsername(username)
                            .orElseThrow(StudentNotFoundException::new);
                    PersonalHomework personalHomework = PersonalHomework.builder()
                            .id(new PersonalHomeworkPk(homework.getId(), username))
                            .status(PersonalHomeworkStatus.ASSIGNED)
                            .submittedAt(null)
                            .content(null)
                            .homework(homework)
                            .student(student)
                            .build();
                    personalHomeworkRepository.save(personalHomework);
                });

        homework.setPersonalHomeworks(personalHomeworkRepository.findByIdHomeworkId(homework.getId()));
        homeworkRepository.save(homework);

    }

    @Override
    @Transactional
    public void submitHomework(int id, String username, String content, List<MultipartFile> files) {
        checkHomeworkService.checkPersonalHomework(id, username);
        submitPersonalHomework(id, username, content);

        if (!CollectionUtils.isEmpty(files)) {
            submitHomeworkFile(id, username, content, files);
        }

    }

    @Override
    @Transactional
    public void resubmitHomework(int id, String username, String content, List<MultipartFile> files) {
        checkHomeworkService.checkPersonalHomework(id, username);
        submitPersonalHomework(id, username, content);

        fileUploader.deleteHomeworkFile(id, username);

        if (!CollectionUtils.isEmpty(files)) {
            submitHomeworkFile(id, username, content, files);
        }

    }

    private void submitPersonalHomework(int id, String username, String content) {
        PersonalHomework personalHomework = PersonalHomework.builder()
                .id(new PersonalHomeworkPk(id, username))
                .status(PersonalHomeworkStatus.SUBMITTED)
                .submittedAt(LocalDateTime.now())
                .content(content)
                .build();
        personalHomeworkRepository.save(personalHomework);
    }

    private void submitHomeworkFile(int id, String username, String content, List<MultipartFile> files) {
        PersonalHomework personalHomework = personalHomeworkRepository.findById(new PersonalHomeworkPk(id, username))
                .orElseThrow(() -> new HomeworkNotFoundException(id, username));
        PersonalHomeworkPk personalHomeworkPk = new PersonalHomeworkPk(id, username);

        files.forEach((file) -> {
            HomeworkFile homeworkFile = HomeworkFile.builder()
                    .id(new HomeworkFilePk(personalHomeworkPk, fileUploader.uploadFile(username, file)))
                    .build();
            homeworkFileRepository.save(homeworkFile);
        });

        personalHomework.setHomeworkFile(homeworkFileRepository.findByIdPersonalHomeworkPk(personalHomeworkPk));
        personalHomeworkRepository.save(personalHomework);
    }

    @Override
    public void changeHomeworkStatus(int id, UserRequest request) {
        String username = request.getUsername();
        PersonalHomework personalHomework = personalHomeworkRepository.findById(new PersonalHomeworkPk(id, username))
                .orElseThrow(() -> new HomeworkNotFoundException(id, username));
        switch (request.getStatus()) {
            case "RETURN":
                personalHomework.setStatus(PersonalHomeworkStatus.RETURNED);
                break;
            case "FINISH":
                personalHomework.setStatus(PersonalHomeworkStatus.FINISHED);
                break;
            default:
                throw new InvalidInputValueException();
        }
        personalHomeworkRepository.save(personalHomework);
    }

    @Override
    public void changeHomework(int id, HomeworkRequest request) {
        homeworkRepository.findById(id)
                .orElseThrow(() -> new HomeworkNotFoundException(id));

        List<String> users = request.getUsernames();
        List<String> originUsers = usersRetrieveService.getOriginUsers(id);
        List<String> deleteUsers = usersRetrieveService.getDeleteUsers(users, originUsers);
        List<String> addUsers = usersRetrieveService.getAddUsers(users, originUsers);

        for(String user : deleteUsers){
            fileUploader.deleteHomeworkFile(id, user);
            personalHomeworkRepository.deleteById(new PersonalHomeworkPk(id, user));
        }

        Homework homework = Homework.builder()
                .id(id)
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .deadline(request.getDeadline())
                .build();
        homeworkRepository.save(homework);

        for(String user : addUsers){
            Student student = studentRepository.findByUsername(user)
                    .orElseThrow(StudentNotFoundException::new);
            PersonalHomework personalHomework = PersonalHomework.builder()
                    .id(new PersonalHomeworkPk(id, user))
                    .status(PersonalHomeworkStatus.ASSIGNED)
                    .submittedAt(null)
                    .content(null)
                    .homework(homework)
                    .student(student)
                    .build();
            personalHomeworkRepository.save(personalHomework);
        }

        homework.setPersonalHomeworks(personalHomeworkRepository.findByIdHomeworkId(id));
        homeworkRepository.save(homework);
    }


    @Override public void deleteHomework(int id) {
        homeworkRepository.findById(id).orElseThrow(() -> new HomeworkNotFoundException(id));

        List<String> users = usersRetrieveService.getOriginUsers(id);

        for(String user : users){
            if (!homeworkFileRepository.findByIdPersonalHomeworkPk(new PersonalHomeworkPk(id, user)).isEmpty()) {
                fileUploader.deleteHomeworkFile(id, user);
            }
            personalHomeworkRepository.deleteById(new PersonalHomeworkPk(id, user));
        }

        homeworkRepository.deleteById(id);
    }
}