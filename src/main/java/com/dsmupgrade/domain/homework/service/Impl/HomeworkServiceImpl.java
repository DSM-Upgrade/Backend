package com.dsmupgrade.domain.homework.service.Impl;

import com.dsmupgrade.domain.homework.domain.*;
import com.dsmupgrade.domain.homework.dto.request.HomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.PersonalHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.UserRequest;
import com.dsmupgrade.domain.homework.dto.response.HomeworkContentResponse;
import com.dsmupgrade.domain.homework.dto.response.HomeworkListResponse;
import com.dsmupgrade.domain.homework.service.CheckFacade;
import com.dsmupgrade.domain.homework.service.HomeworkService;
import com.dsmupgrade.domain.homework.service.S3HomeworkFileUploader;
import com.dsmupgrade.domain.homework.service.UsersFacade;
import com.dsmupgrade.domain.student.domain.StudentRepository;
import com.dsmupgrade.global.error.exception.HomeworkNotFoundException;
import com.dsmupgrade.global.error.exception.InvalidInputValueException;
import com.dsmupgrade.global.error.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    private final UsersFacade usersFacade;
    private final CheckFacade checkFacade;

    @Override
    public List<HomeworkListResponse> getHomeworkList(String username) {
        checkFacade.checkTimeOut(username);
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

        homework.setPersonalHomeworks(
                request.getUsernames().stream().map((username) -> {
                    PersonalHomework personalHomework = PersonalHomework.builder()
                            .id(new PersonalHomeworkPk(homework.getId(), username))
                            .status(PersonalHomeworkStatus.ASSIGNED)
                            .submittedAt(null)
                            .content(null)
                            .homework(homework)
                            .build();
                    personalHomeworkRepository.save(personalHomework);
                    return personalHomework;
                }).collect(Collectors.toList()));

        homeworkRepository.save(homework);

    }

    @Override
    @Transactional
    public void submitHomework(int id, String username, PersonalHomeworkRequest request) {
        checkFacade.checkPersonalHomework(id, username);
        submitPersonalHomework(id, username, request.getContent());

        if (request.getFiles() != null) {
            submitHomeworkFile(id, username, request);
        }

    }

    @Override
    @Transactional
    public void resubmitHomework(int id, String username, PersonalHomeworkRequest request) {
        checkFacade.checkPersonalHomework(id, username);
        submitPersonalHomework(id, username, request.getContent());

        fileUploader.deleteHomeworkFile(id, username);

        if (request.getFiles() != null) {
            submitHomeworkFile(id, username, request);
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

    private void submitHomeworkFile(int id, String username, PersonalHomeworkRequest request) {
        PersonalHomework personalHomework = personalHomeworkRepository.findById(new PersonalHomeworkPk(id, username))
                .orElseThrow(() -> new HomeworkNotFoundException(id, username));

        personalHomework.setHomeworkFile(
                request.getFiles().stream().map((file) -> {
                    HomeworkFile homeworkFile = HomeworkFile.builder()
                            .id(new HomeworkFilePk(id, username, fileUploader.uploadFile(username, file)))
                            .personalHomework(personalHomeworkRepository.findById(new PersonalHomeworkPk(id, username)).get())
                            .build();
                    homeworkFileRepository.save(homeworkFile);
                    return homeworkFile;
                }).collect(Collectors.toList()));
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
    }

    @Override
    public void changeHomework(int id, HomeworkRequest request) {
        homeworkRepository.findById(id)
                .orElseThrow(() -> new HomeworkNotFoundException(id));

        List<String> users = request.getUsernames();
        List<String> originUsers = usersFacade.getOriginUsers(id);
        List<String> deleteUsers = usersFacade.getDeleteUsers(users, originUsers);
        List<String> addUsers = usersFacade.getAddUsers(users, originUsers);

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
            PersonalHomework personalHomework = PersonalHomework.builder()
                    .id(new PersonalHomeworkPk(id, user))
                    .status(PersonalHomeworkStatus.ASSIGNED)
                    .submittedAt(null)
                    .content(null)
                    .homework(homework)
                    .build();
            personalHomeworkRepository.save(personalHomework);
        }

        homework.setPersonalHomeworks(personalHomeworkRepository.findByIdHomeworkId(id));
        homeworkRepository.save(homework);
    }


    @Override public void deleteHomework(int id) {
        homeworkRepository.findById(id).orElseThrow(() -> new HomeworkNotFoundException(id));

        List<String> users = usersFacade.getOriginUsers(id);

        for(String user : users){
            if (!homeworkFileRepository.findByIdHomeworkIdAndIdUsername(id, user).isEmpty()) {
                fileUploader.deleteHomeworkFile(id, user);
            }
            personalHomeworkRepository.deleteById(new PersonalHomeworkPk(id, user));
        }

        homeworkRepository.deleteById(id);
    }
}