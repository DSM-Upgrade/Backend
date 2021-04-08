package com.dsmupgrade.domain.student.service;

import com.dsmupgrade.domain.field.domain.Field;
import com.dsmupgrade.domain.field.domain.FieldRepository;
import com.dsmupgrade.domain.student.domain.Student;
import com.dsmupgrade.domain.student.domain.StudentRepository;
import com.dsmupgrade.domain.student.dto.request.PasswordRequest;
import com.dsmupgrade.domain.student.dto.request.UpdateStudentRequest;
import com.dsmupgrade.global.error.exception.FieldNotFoundException;
import com.dsmupgrade.global.error.exception.InvalidInputValueException;
import com.dsmupgrade.global.error.exception.PasswordNotMatchedException;
import com.dsmupgrade.global.error.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class StudentUpdateService {

    private final StudentRepository studentRepository;
    private final FieldRepository fieldRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3ImageUploader imageUploader;

    public void updateStudentPassword(String username, PasswordRequest passwordRequest) {
        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new StudentNotFoundException(username));

        if (!passwordEncoder.matches(passwordRequest.getNowPassword(), student.getPassword())) {
            throw new PasswordNotMatchedException();
        }

        String newPassword = passwordEncoder.encode(passwordRequest.getNewPassword());
        student.updatePassword(newPassword);
        studentRepository.save(student);
    }

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
