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
import com.dsmupgrade.global.error.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeworkServiceImpl implements HomeworkService{
    private final PersonalHomeworkRepository personalHomeworkRepository;
    private final HomeworkRepository homeworkRepository;
    private final StudentRepository studentRepository;

    @Override
    public List<UserAllHomeworkListResponse> getHomeworkList(String username){ // 유저마다 할당된 숙제의 리스트를 받아옴 (반환은 되었지만, 완료가 되지 않은 것도 포함)
        return personalHomeworkRepository.findAllByStudentUsername(username)
                .stream().map(UserAllHomeworkListResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public UserHomeworkResponse getUserHomework (String username, int homeworkId){ // 유저마다 할당된 숙제의 내용을 받아옴 (반환은 되었지만, 완료가 되지 않은 것도 포함)
        PersonalHomeworkPk personalHomeworkPk = PersonalHomeworkPk.builder()
                .homeworkId(homeworkId)
                .studentUsername(username)
                .build();
        return UserHomeworkResponse.from(personalHomeworkRepository.findById(personalHomeworkPk).orElseThrow(()-> new HomeworkNotFoundException(homeworkId)));
    }

    @Override
    public void assignmentHomework(AssignmentHomeworkRequest assignmentHomeworkRequest){ // 유저에게 숙제 할당
        /*assignmentHomeworkRequest.getUserName()
                .stream()
                .forEach((username)->{
                    studentRepository.findByUsername(username).orElseThrow(()->new StudentNotFoundException(username));
                });*/
        studentRepository.existsAllByUsername(assignmentHomeworkRequest.getUserName());
        Calendar time = Calendar.getInstance();
        Homework homework = Homework.builder()
                .title(assignmentHomeworkRequest.getHomeworkTitle())
                .content(assignmentHomeworkRequest.getHomeworkContent())
                .createdAt(time.getTime())
                .deadline(assignmentHomeworkRequest.getDeadline())
                .build();
        homeworkRepository.save(homework);
        assignmentHomeworkRequest.getUserName()
                .stream()
                .forEach((username) -> {
                    PersonalHomework personalHomework = PersonalHomework.builder()
                            .studentUsername(username)
                            .status(PersonalHomeworkStatus.ASSIGNED)
                            .submittedAt(time.getTime())
                            .content(null)
                            .homeworkId(homework.getId())
                            .build();
                    personalHomeworkRepository.save(personalHomework);
                });
    }

    @Override
    public void returnHomework(ReturnHomeworkRequest returnHomeworkRequest){ // 숙제 반환
        PersonalHomeworkPk personalHomeworkPk = PersonalHomeworkPk.builder()
                .homeworkId(returnHomeworkRequest.getHomeworkId())
                .studentUsername(returnHomeworkRequest.getUserName())
                .build();
        Calendar time = Calendar.getInstance();
        boolean isEmpty = isEmptyPersonalHomework(personalHomeworkPk);
        if (isEmpty) {
            throw new HomeworkNotFoundException(returnHomeworkRequest.getHomeworkId());
        } else {
            PersonalHomework findPersonalHomework = personalHomeworkRepository.findById(personalHomeworkPk)
                    .orElseThrow(()->new HomeworkNotFoundException(returnHomeworkRequest.getHomeworkId()));
            PersonalHomework personalHomework = PersonalHomework.builder()
                    .studentUsername(findPersonalHomework.getStudentUsername())
                    .status(PersonalHomeworkStatus.SUBMITTED)
                    .submittedAt(time.getTime())
                    .content(returnHomeworkRequest.getHomeworkContent())
                    .homeworkId(findPersonalHomework.getHomeworkId())
                    .build();
            personalHomeworkRepository.save(personalHomework);
        }
    }

    private boolean isEmptyHomework(int id){
        return homeworkRepository.findById(id).isEmpty();
    }

    private boolean isEmptyPersonalHomework(PersonalHomeworkPk personalHomeworkPk){
        return personalHomeworkRepository.findById(personalHomeworkPk).isEmpty();
    }

    @Override
    public void completionHomework(CompletionHomeworkRequest completionHomeworkRequest){ // 숙제 완료
        PersonalHomeworkPk personalHomeworkPk = PersonalHomeworkPk.builder()
                .homeworkId(completionHomeworkRequest.getHomeworkId())
                .studentUsername(completionHomeworkRequest.getUserName())
                .build();
        boolean isEmpty = isEmptyPersonalHomework(personalHomeworkPk);
        if (isEmpty) {
            throw new HomeworkNotFoundException(completionHomeworkRequest.getHomeworkId());
        } else {
            PersonalHomework findPersonalHomework = personalHomeworkRepository.findById(personalHomeworkPk)
                    .orElseThrow(()->new HomeworkNotFoundException(completionHomeworkRequest.getHomeworkId()));
            PersonalHomework personalHomework = PersonalHomework.builder()
                    .studentUsername(findPersonalHomework.getStudentUsername())
                    .status(PersonalHomeworkStatus.FINISHED)
                    .submittedAt(findPersonalHomework.getSubmittedAt())
                    .content(findPersonalHomework.getContent())
                    .homeworkId(findPersonalHomework.getHomeworkId())
                    .build();
            personalHomeworkRepository.save(personalHomework);
        }
    }

    @Override
    public void changeHomework(ChangeHomeworkRequest changeHomeworkRequest){ // 할당한 숙제의 내용을 변경
        int homeworkId = changeHomeworkRequest.getHomeworkId();
        if(isEmptyHomework(homeworkId)) throw new HomeworkNotFoundException(homeworkId);
        /*changeHomeworkRequest.getUserName()
                .stream()
                .forEach((username)->{
                    studentRepository.findByUsername(username).orElseThrow(()->new StudentNotFoundException(username));
                });*/
        studentRepository.existsAllByUsername(changeHomeworkRequest.getUserName());
        personalHomeworkRepository.deleteByHomeworkId(homeworkId);
        Calendar time = Calendar.getInstance();
        Homework homework = Homework.builder()
                .title(changeHomeworkRequest.getHomeworkTitle())
                .content(changeHomeworkRequest.getHomeworkContent())
                .createdAt(time.getTime())
                .deadline(changeHomeworkRequest.getDeadline())
                .build();
        homeworkRepository.save(homework);
        changeHomeworkRequest.getUserName()
                .stream()
                .forEach((username) -> {
                    PersonalHomework personalHomework = PersonalHomework.builder()
                            .studentUsername(username)
                            .status(PersonalHomeworkStatus.ASSIGNED)
                            .submittedAt(time.getTime())
                            .content(null)
                            .homeworkId(homework.getId())
                            .build();
                    personalHomeworkRepository.save(personalHomework);
                });
    }

    @Override
    public void deleteHomework(Integer homeworkId){ // 숙제를 삭제
        personalHomeworkRepository.deleteByHomeworkId(homeworkId);
        homeworkRepository.deleteById(homeworkId);
    }
}
