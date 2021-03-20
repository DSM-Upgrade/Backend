package com.dsmupgrade.domain.homework.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonalHomeworkRepository extends JpaRepository<PersonalHomework, PersonalHomeworkPk> {
    List<PersonalHomework> findAllByStudentUsername(String username);
    Optional<PersonalHomework> findById(PersonalHomeworkPk personal_homeworkPk);
    void deleteByHomeworkId (int id);
}
