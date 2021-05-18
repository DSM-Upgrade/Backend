package com.dsmupgrade.domain.homework.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonalHomeworkRepository extends JpaRepository<PersonalHomework, Integer> {
    Optional<PersonalHomework> findByHomeworkId(Integer integer);
    List<PersonalHomework> findByIdStudentUsername(String username);
    Optional<PersonalHomework> findById(PersonalHomeworkPk id);
    void deleteByHomeworkId(Integer id);
}
