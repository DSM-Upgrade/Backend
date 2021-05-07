package com.dsmupgrade.domain.student.infrastructure.repository;

import com.dsmupgrade.domain.student.domain.Student;
import com.dsmupgrade.domain.student.domain.StudentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentJpaRepository extends StudentRepository, JpaRepository<Student, String> {
}
