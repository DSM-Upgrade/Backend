package com.dsmupgrade.service;

import com.dsmupgrade.domain.entity.Student;
import com.dsmupgrade.domain.repository.StudentRepository;
import com.dsmupgrade.dto.response.StudentResponse;
import com.dsmupgrade.global.error.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public StudentResponse getStudentByUsername(String username) {
        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new StudentNotFoundException(username));

        return StudentResponse.of(student);
    }
}
