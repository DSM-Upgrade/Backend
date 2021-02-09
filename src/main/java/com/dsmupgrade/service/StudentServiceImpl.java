package com.dsmupgrade.service;

import com.dsmupgrade.domain.entity.Field;
import com.dsmupgrade.domain.entity.Student;
import com.dsmupgrade.domain.repository.FieldRepository;
import com.dsmupgrade.domain.repository.StudentRepository;
import com.dsmupgrade.dto.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final FieldRepository fieldRepository;

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        Field field = fieldRepository.findById(signUpRequest.getFieldId())
                .orElseThrow(); // TODO exception

        Student student = Student.builder()
                .username(signUpRequest.getUsername())
                .password(signUpRequest.getPassword()) // TODO password encoding required
                .grade(signUpRequest.getGrade())
                .cls(signUpRequest.getCls())
                .number(signUpRequest.getNumber())
                .name(signUpRequest.getName())
                .field(field)
                .build();

        studentRepository.save(student);
    }
}
