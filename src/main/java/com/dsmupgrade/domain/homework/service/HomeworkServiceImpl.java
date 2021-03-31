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
        Homework homework = homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new HomeworkNotFoundException(homeworkId));

        PersonalHomeworkPk personalHomeworkPk = PersonalHomeworkPk.builder()
                .homework(homework)
                .studentUsername(username)
                .build();

        return UserHomeworkResponse.from(personalHomeworkRepository.findById(personalHomeworkPk).orElseThrow(()-> new HomeworkNotFoundException(homeworkId)));
    }

    @Override
    public void assignmentHomework(AssignmentHomeworkRequest assignmentHomeworkRequest){ // 유저에게 숙제 할당
        studentRepository.existsByUsernameIn(assignmentHomeworkRequest.getUserName());
        Homework homework = Homework.builder()
                .title(assignmentHomeworkRequest.getHomeworkTitle())
                .content(assignmentHomeworkRequest.getHomeworkContent())
                .createdAt(Calendar.getInstance().getTime())
                .deadline(assignmentHomeworkRequest.getDeadline())
                .build();
        homeworkRepository.save(homework);
        assignmentHomeworkRequest.getUserName()
                .stream()
                .forEach((username) -> {
                    PersonalHomework personalHomework = PersonalHomework.builder()
                            .studentUsername(username)
                            .status(PersonalHomeworkStatus.ASSIGNED)
                            .submittedAt(Calendar.getInstance().getTime())
                            .content(null)
                            .homework(homework)
                            .build();
                    personalHomeworkRepository.save(personalHomework);
                });
    }

    @Override
    public void returnHomework(ReturnHomeworkRequest returnHomeworkRequest){ // 숙제 반환
        int homeworkId = returnHomeworkRequest.getHomeworkId();
        Homework homework = homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new HomeworkNotFoundException(homeworkId));
        PersonalHomeworkPk personalHomeworkPk = PersonalHomeworkPk.builder()
                .homework(homework)
                .studentUsername(returnHomeworkRequest.getUserName())
                .build();
        boolean isEmpty = isEmptyPersonalHomework(personalHomeworkPk);
        if (isEmpty) {
            throw new HomeworkNotFoundException(homeworkId);
        } else {
            PersonalHomework findPersonalHomework = personalHomeworkRepository.findById(personalHomeworkPk)
                    .orElseThrow(()->new HomeworkNotFoundException(homeworkId));
            PersonalHomework personalHomework = PersonalHomework.builder()
                    .studentUsername(findPersonalHomework.getStudentUsername())
                    .status(PersonalHomeworkStatus.SUBMITTED)
                    .submittedAt(Calendar.getInstance().getTime())
                    .content(returnHomeworkRequest.getHomeworkContent())
                    .homework(findPersonalHomework.getHomework())
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
        int homeworkId = completionHomeworkRequest.getHomeworkId();
        Homework homework = homeworkRepository.findById(homeworkId)
                .orElseThrow(()->new HomeworkNotFoundException(homeworkId));
        PersonalHomeworkPk personalHomeworkPk = PersonalHomeworkPk.builder()
                .homework(homework)
                .studentUsername(completionHomeworkRequest.getUserName())
                .build();
        boolean isEmpty = isEmptyPersonalHomework(personalHomeworkPk);
        if (isEmpty) {
            throw new HomeworkNotFoundException(homeworkId);
        } else {
            PersonalHomework findPersonalHomework = personalHomeworkRepository.findById(personalHomeworkPk)
                    .orElseThrow(()->new HomeworkNotFoundException(homeworkId));
            PersonalHomework personalHomework = PersonalHomework.builder()
                    .studentUsername(findPersonalHomework.getStudentUsername())
                    .status(PersonalHomeworkStatus.FINISHED)
                    .submittedAt(findPersonalHomework.getSubmittedAt())
                    .content(findPersonalHomework.getContent())
                    .homework(findPersonalHomework.getHomework())
                    .build();
            personalHomeworkRepository.save(personalHomework);
        }
    }

    @Override
    public void changeHomework(ChangeHomeworkRequest changeHomeworkRequest){ // 할당한 숙제의 내용을 변경
        int homeworkId = changeHomeworkRequest.getHomeworkId();
        if(isEmptyHomework(homeworkId)) throw new HomeworkNotFoundException(homeworkId);
        studentRepository.existsByUsernameIn(changeHomeworkRequest.getUserName());
        personalHomeworkRepository.deleteByHomeworkId(homeworkId);
        Homework homework = Homework.builder()
                .title(changeHomeworkRequest.getHomeworkTitle())
                .content(changeHomeworkRequest.getHomeworkContent())
                .createdAt(Calendar.getInstance().getTime())
                .deadline(changeHomeworkRequest.getDeadline())
                .build();
        homeworkRepository.save(homework);
        changeHomeworkRequest.getUserName()
                .stream()
                .forEach((username) -> {
                    PersonalHomework personalHomework = PersonalHomework.builder()
                            .studentUsername(username)
                            .status(PersonalHomeworkStatus.ASSIGNED)
                            .submittedAt(Calendar.getInstance().getTime())
                            .content(null)
                            .homework(homework)
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
