package com.dsmupgrade.domain.repository;

import com.dsmupgrade.domain.entity.Personal_homework;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Personal_homeworkRepository extends JpaRepository<Personal_homework, Personal_homeworkPk> {
    List<Personal_homework> findAllByStudentUsername(String username);
    Optional<Personal_homework> findById(Personal_homeworkPk personal_homeworkPk);
}
