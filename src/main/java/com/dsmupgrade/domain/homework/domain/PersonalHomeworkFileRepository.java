package com.dsmupgrade.domain.homework.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalHomeworkFileRepository extends JpaRepository<PersonalHomeworkFile,Integer> {
    void deleteByPersonalHomework(PersonalHomework personalHomework);
    boolean existsByNameIn(String name);
}
