package com.dsmupgrade.domain.repository;

import com.dsmupgrade.domain.entity.Student;

import java.util.Optional;

public interface StudentRepository {

    Student save(Student student);
    Optional<Student> findByUsername(String username);
}
