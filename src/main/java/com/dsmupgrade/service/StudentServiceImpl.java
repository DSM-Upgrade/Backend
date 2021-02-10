package com.dsmupgrade.service;

import com.dsmupgrade.domain.entity.Field;
import com.dsmupgrade.domain.entity.Student;
import com.dsmupgrade.domain.repository.FieldRepository;
import com.dsmupgrade.domain.repository.StudentRepository;
import com.dsmupgrade.dto.request.SignUpRequest;
import com.dsmupgrade.exception.FieldNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final FieldRepository fieldRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        Field field = fieldRepository.findById(signUpRequest.getFieldId())
                .orElseThrow(() -> new FieldNotFoundException(signUpRequest.getFieldId()));

        Student student = Student.builder()
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .studentNum(signUpRequest.getStudentNum())
                .name(signUpRequest.getName())
                .field(field)
                .build();

        studentRepository.save(student);
    }
}
