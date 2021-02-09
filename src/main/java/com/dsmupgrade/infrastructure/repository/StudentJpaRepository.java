package com.dsmupgrade.infrastructure.repository;

import com.dsmupgrade.domain.entity.Student;
import com.dsmupgrade.domain.repository.StudentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentJpaRepository extends StudentRepository, JpaRepository<Student, String> {

    @Override
    Student save(Student student);
}
