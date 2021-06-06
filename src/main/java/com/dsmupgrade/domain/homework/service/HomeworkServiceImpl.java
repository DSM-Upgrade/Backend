package com.dsmupgrade.domain.homework.service;

import com.dsmupgrade.domain.homework.domain.*;
import com.dsmupgrade.domain.homework.dto.request.HomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.PersonalHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.response.HomeworkContentResponse;
import com.dsmupgrade.domain.homework.dto.response.HomeworkListResponse;
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

    @Override
    public List<HomeworkListResponse> getHomeworkList(String username) {
        return personalHomeworkRepository.findByIdStudentUsername(username)
                .stream().map(HomeworkListResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public HomeworkContentResponse getHomeworkContent(String username, int id) {
        return HomeworkContentResponse.from(
                personalHomeworkRepository.findById(new PersonalHomeworkPk(id, username))
                        .orElseThrow(()-> new HomeworkNotFoundException(id, username))
        );
    }

    @Override
    @Transactional
    public void assignmentHomework(HomeworkRequest request) {
        if(studentRepository.existsByUsernameIn(request.getUsernames())) {
            throw new StudentNotFoundException();
        }
        Homework homework = Homework.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .deadline(request.getDeadline())
                .build();
        // TODO 유저에게 숙제 할당
    }

    @Override
    public void submitHomework(int id, PersonalHomeworkRequest request) {
        // TODO 숙제 제출
    }

    @Override
    public void finishHomework(int id, String username) {
        // TODO 숙제 완료
    }

    @Override
    public void returnHomework(int id, String username) {
        // TODO 숙제 반환
    }

    @Override
    public void changeHomework(int id, HomeworkRequest request) {
        // TODO 할당한 숙제의 내용을 변경
    }

    @Override
    public void deleteHomework(int id) {
        // TODO 숙제를 삭제
    }

    private String uploadFile(String username, MultipartFile file) {
        try {
            return fileUploader.upload(username, file, "homework");
        } catch (IOException exception) {
            throw new InvalidInputValueException(); // TODO change exception
        }
    }
}
