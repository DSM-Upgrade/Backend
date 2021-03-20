package com.dsmupgrade.domain.student.domain;

import java.util.Optional;

public interface StudentRepository {

    Student save(Student student);
    Optional<Student> findByUsername(String username);
}
