package com.dsmupgrade.domain.homework.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeworkFileRepository extends JpaRepository<HomeworkFile, Integer> {
    void deleteByHomework(Homework homework);
    boolean existsByNameIn(String name);
}
