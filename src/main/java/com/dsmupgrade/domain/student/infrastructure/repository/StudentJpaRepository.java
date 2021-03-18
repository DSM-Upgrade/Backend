package com.dsmupgrade.domain.student.infrastructure.repository;

import com.dsmupgrade.domain.student.domain.Student;
import com.dsmupgrade.domain.student.domain.StudentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentJpaRepository extends StudentRepository, JpaRepository<Student, String> {
}
