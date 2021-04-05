package com.dsmupgrade.domain.homework.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonalHomeworkRepository extends JpaRepository<PersonalHomework, Integer> {
    List<PersonalHomework> findByStudentUsername(String username);
    Optional<PersonalHomework> findByStudentUsernameAndHomework(String username, Homework homework);
    void deleteByHomeworkId(Integer id);
}
