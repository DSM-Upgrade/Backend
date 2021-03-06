package com.dsmupgrade.domain.homework.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface PersonalHomeworkRepository extends JpaRepository<PersonalHomework, PersonalHomeworkPk> {
    List<PersonalHomework> findByIdStudentUsername(String username);
    List<PersonalHomework> findByIdHomeworkId(int id);
    Optional<PersonalHomework> findById(PersonalHomeworkPk id);
    void deleteById(PersonalHomeworkPk id);
}
