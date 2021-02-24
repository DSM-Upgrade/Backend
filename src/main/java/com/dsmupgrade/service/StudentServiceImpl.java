package com.dsmupgrade.service;

import com.dsmupgrade.domain.entity.Field;
import com.dsmupgrade.domain.entity.Student;
import com.dsmupgrade.domain.repository.FieldRepository;
import com.dsmupgrade.domain.repository.StudentRepository;
import com.dsmupgrade.dto.request.PasswordRequest;
import com.dsmupgrade.dto.request.UpdateStudentRequest;
import com.dsmupgrade.dto.response.StudentResponse;
import com.dsmupgrade.global.error.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final FieldRepository fieldRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageUploader imageUploader;

    @Override
    public StudentResponse getStudentByUsername(String username) {
        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new StudentNotFoundException(username));

        return StudentResponse.of(student);
    }

    @Override
    public void updateStudentPassword(String username, PasswordRequest passwordRequest) {
        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new StudentNotFoundException(username));

        if (!passwordEncoder.matches(passwordRequest.getNowPassword(), student.getPassword())) {
            throw new PasswordNotMatchedException();
        }

        student.updatePassword(passwordRequest.getNewPassword());
        studentRepository.save(student);
    }

    @Override
    public void updateStudentInfo(String username, UpdateStudentRequest updateStudentRequest) {
        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new StudentNotFoundException(username));

        student.updateStudentNum(updateStudentRequest.getStudentNum());

        if (updateStudentRequest.getFieldId() != null) {
            Field field = fieldRepository.findById(updateStudentRequest.getFieldId())
                    .orElseThrow(() -> new FieldNotFoundException(updateStudentRequest.getFieldId()));

            student.updateField(field);
        }

        studentRepository.save(student);
    }

    @Override
    public String updateStudentProfile(String username, MultipartFile file) {
        return upload(username, file, "profile");
    }

    private String upload(String username, MultipartFile file, String dir) {
        try {
            return imageUploader.upload(username, file, dir);
        } catch (IOException exception) {
            throw new InvalidInputValueException(); // TODO change exception
        }
    }
}
