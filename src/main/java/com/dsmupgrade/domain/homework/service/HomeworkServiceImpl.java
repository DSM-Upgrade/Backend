package com.dsmupgrade.domain.homework.service;

import com.dsmupgrade.domain.homework.domain.*;
import com.dsmupgrade.domain.homework.dto.request.AssignmentHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.ChangeHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.CompletionHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.ReturnHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.response.UserAllHomeworkListResponse;
import com.dsmupgrade.domain.homework.dto.response.UserHomeworkResponse;
import com.dsmupgrade.domain.student.domain.StudentRepository;
import com.dsmupgrade.global.error.exception.HomeworkNotFoundException;
import com.dsmupgrade.global.error.exception.InvalidInputValueException;
import com.dsmupgrade.global.error.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeworkServiceImpl implements HomeworkService{
    private final PersonalHomeworkRepository personalHomeworkRepository;
    private final HomeworkRepository homeworkRepository;
    private final StudentRepository studentRepository;
    private final S3HomeworkFileUploader fileUploader;
    private final HomeworkFileRepository homeworkFileRepository;
    private final PersonalHomeworkFileRepository personalHomeworkFileRepository;

    private void checkTimeOut(){
        List<PersonalHomework> homeworkList= personalHomeworkRepository.findAll();
        homeworkList.forEach(
                (homework) -> {
                    if(homework.getHomework().getDeadline().isBefore(LocalDateTime.now())
                            && homework.getStatus() == PersonalHomeworkStatus.ASSIGNED){
                        homework.setStatus(PersonalHomeworkStatus.UN_SUBMITTED);
                    }
                    personalHomeworkRepository.save(homework);
                }
        );
    }

    @Override
    public List<UserAllHomeworkListResponse> getHomeworkList(String username){ // 유저마다 할당된 숙제의 리스트를 받아옴 (반환은 되었지만, 완료가 되지 않은 것도 포함)
        checkTimeOut();
        return personalHomeworkRepository.findByIdStudentUsername(username)
                .stream().map(UserAllHomeworkListResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public UserHomeworkResponse getUserHomework (String username, int homeworkId){ // 유저마다 할당된 숙제의 내용을 받아옴 (반환은 되었지만, 완료가 되지 않은 것도 포함)
        checkTimeOut();
        homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new HomeworkNotFoundException(homeworkId));
        studentRepository.findByUsername(username)
                .orElseThrow(()->new StudentNotFoundException(username));
        if(personalHomeworkRepository.findById(new PersonalHomeworkPk(homeworkId, username)).isEmpty())
            throw new HomeworkNotFoundException(homeworkId);
        return UserHomeworkResponse.from(personalHomeworkRepository.findById(new PersonalHomeworkPk(homeworkId, username)).get());
    }

    @Override
    @Transactional
    public void assignmentHomework(String requestUser, AssignmentHomeworkRequest assignmentHomeworkRequest){ // 유저에게 숙제 할당
        if(!studentRepository.existsByUsernameIn(assignmentHomeworkRequest.getUsername()))
            throw new StudentNotFoundException();

        List<HomeworkFile> homeworkFiles = assignmentHomeworkRequest.getHomeworkFile()==null
                ? null : assignmentHomeworkRequest.getHomeworkFile().stream()
                .map((file)-> {
                    HomeworkFile homeworkFile = HomeworkFile.builder()
                            .name(uploadFile(requestUser, file))
                            .build();
                    homeworkFileRepository.save(homeworkFile);
                    return homeworkFile; }
                ).collect(Collectors.toList());

        Homework homework = Homework.builder()
                .title(assignmentHomeworkRequest.getHomeworkTitle())
                .content(assignmentHomeworkRequest.getHomeworkContent())
                .createdAt(LocalDateTime.now())
                .deadline(assignmentHomeworkRequest.getDeadline())
                .homeworkFile(homeworkFiles)
                .build();
        homeworkRepository.save(homework);
        assignmentHomeworkRequest.getUsername()
                .forEach((username) -> {
                    PersonalHomework personalHomework = PersonalHomework.builder()
                            .id(new PersonalHomeworkPk(requestUser))
                            .status(PersonalHomeworkStatus.ASSIGNED)
                            .submittedAt(null)
                            .content(null)
                            .homework(homework)
                            .personalHomeworkFile(null)
                            .build();
                    personalHomeworkRepository.save(personalHomework);
                });
    }

    @Override
    @Transactional
    public void returnHomework(String requestUser, ReturnHomeworkRequest returnHomeworkRequest){ // 숙제 반환
        int homeworkId = returnHomeworkRequest.getHomeworkId();
        Homework homework = homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new HomeworkNotFoundException(homeworkId));
        studentRepository.findByUsername(requestUser)
                .orElseThrow(() -> new StudentNotFoundException(requestUser));
        PersonalHomework findPersonalHomework = personalHomeworkRepository.findById(new PersonalHomeworkPk(homeworkId, requestUser))
                .orElseThrow(() -> new HomeworkNotFoundException(homeworkId, requestUser));
        List<PersonalHomeworkFile> personalHomeworks = returnHomeworkRequest.getPersonalHomeworkFile()==null
                ? null:returnHomeworkRequest.getPersonalHomeworkFile().stream()
                .map((file) -> {
                            String name = uploadFile(requestUser, file);
                            PersonalHomeworkFile personalHomeworkFile = PersonalHomeworkFile.builder()
                                    .name(name)
                                    .build();
                            personalHomeworkFileRepository.save(personalHomeworkFile);
                            return personalHomeworkFile;
                        }).collect(Collectors.toList());
        PersonalHomework personalHomework = PersonalHomework.builder()
                .id(new PersonalHomeworkPk(findPersonalHomework.getId().getHomeworkId(), requestUser))
                .homework(homework)
                .status(PersonalHomeworkStatus.SUBMITTED)
                .submittedAt(LocalDateTime.now())
                .content(returnHomeworkRequest.getHomeworkContent())
                .homework(findPersonalHomework.getHomework())
                .personalHomeworkFile(personalHomeworks)
                .build();
        personalHomeworkRepository.save(personalHomework);
    }

    @Override
    public void completionHomework(CompletionHomeworkRequest completionHomeworkRequest){ // 숙제 완료
        int homeworkId = completionHomeworkRequest.getHomeworkId();
        String username = completionHomeworkRequest.getUsername();
        studentRepository.findByUsername(username)
                .orElseThrow(() -> new StudentNotFoundException(username));
        Homework homework = homeworkRepository.findById(homeworkId)
                .orElseThrow(()->new HomeworkNotFoundException(homeworkId));
        PersonalHomework findPersonalHomework = personalHomeworkRepository.findById(new PersonalHomeworkPk(homeworkId, username))
                .orElseThrow(() -> new HomeworkNotFoundException(homeworkId, username));

        PersonalHomework personalHomework = PersonalHomework.builder()
                .id(new PersonalHomeworkPk(findPersonalHomework.getId().getHomeworkId(), username))
                .status(PersonalHomeworkStatus.FINISHED)
                .submittedAt(findPersonalHomework.getSubmittedAt())
                .content(findPersonalHomework.getContent())
                .homework(homework)
                .build();
        personalHomeworkRepository.save(personalHomework);
    }

    @Override
    @Transactional
    public void changeHomework(String requestUser, ChangeHomeworkRequest changeHomeworkRequest){ // 할당한 숙제의 내용을 변경
        int homeworkId = changeHomeworkRequest.getHomeworkId();
        homeworkRepository.findById(homeworkId).orElseThrow(() -> new HomeworkNotFoundException(homeworkId));
        if(!studentRepository.existsByUsernameIn(changeHomeworkRequest.getUsername())){
            throw new StudentNotFoundException();
        }

        personalHomeworkRepository.deleteByHomeworkId(homeworkId);

        Homework homework = Homework.builder()
                .id(changeHomeworkRequest.getHomeworkId())
                .title(changeHomeworkRequest.getHomeworkTitle())
                .content(changeHomeworkRequest.getHomeworkContent())
                .createdAt(LocalDateTime.now())
                .deadline(changeHomeworkRequest.getDeadline())
                .build();
        homeworkRepository.save(homework);

        deletePersonalHomeworkFile(homeworkId);

        changeHomeworkRequest.getUsername()
                .forEach((username) -> {
                    PersonalHomework personalHomework = PersonalHomework.builder()
                            .id(new PersonalHomeworkPk(username))
                            .status(PersonalHomeworkStatus.ASSIGNED)
                            .submittedAt(LocalDateTime.now())
                            .content(null)
                            .homework(homework)
                            .build();
                    personalHomeworkRepository.save(personalHomework);
                });

        List<HomeworkFile> homeworkFiles = changeHomeworkRequest.getHomeworkFile()==null
                ? null : changeHomeworkRequest.getHomeworkFile().stream()
                .map((file)-> {
                    HomeworkFile homeworkFile = HomeworkFile.builder()
                            .name(uploadFile(requestUser, file))
                            .build();
                    homeworkFileRepository.save(homeworkFile);
                    return homeworkFile; }
                ).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteHomework(Integer homeworkId){ // 숙제를 삭제
        homeworkFileRepository
                .deleteByHomework(homeworkRepository
                        .findById(homeworkId).orElseThrow(()->new HomeworkNotFoundException(homeworkId)));
        personalHomeworkFileRepository
                .deleteByPersonalHomework(personalHomeworkRepository
                        .findByHomeworkId(homeworkId).orElseThrow(()->new HomeworkNotFoundException(homeworkId)));
        deletePersonalHomeworkFile(homeworkId);
        deleteHomeworkFile(homeworkId);
        personalHomeworkRepository.deleteByHomeworkId(homeworkId);
        homeworkRepository.deleteById(homeworkId);
    }

    private void deletePersonalHomeworkFile(Integer homeworkId){
        personalHomeworkRepository.findByHomeworkId(homeworkId).ifPresent(
                (personalHomework)->{
                    if(!(personalHomework.getPersonalHomeworkFile()==null)) {
                        personalHomework.getPersonalHomeworkFile()
                                .forEach(
                                        (file) -> {
                                            fileUploader.delete(file.getName());
                                        }
                                );
                    }
                }
        );
    }

    private void deleteHomeworkFile(Integer homeworkId){
        homeworkRepository.findById(homeworkId)
                .stream().forEach(
                (homework)->{
                    if(!(homework.getHomeworkFile()==null)) {
                        homework.getHomeworkFile().stream()
                                .forEach(
                                        (file) -> {
                                            fileUploader.delete(file.getName());
                                        }
                                );
                    }
                }
        );
    }

    private String uploadFile(String username, MultipartFile file) {
        try {
            return fileUploader.upload(username, file, "homework");
        } catch (IOException exception) {
            throw new InvalidInputValueException(); // TODO change exception
        }
    }
}
