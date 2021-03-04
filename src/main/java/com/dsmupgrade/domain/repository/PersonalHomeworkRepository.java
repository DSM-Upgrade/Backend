package com.dsmupgrade.domain.repository;

import com.dsmupgrade.domain.entity.PersonalHomework;
import com.dsmupgrade.domain.entity.PersonalHomeworkPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonalHomeworkRepository extends JpaRepository<PersonalHomework, PersonalHomeworkPk> {
    List<PersonalHomework> findAllByStudentUsername(String username);
    Optional<PersonalHomework> findById(PersonalHomeworkPk personal_homeworkPk);
}
