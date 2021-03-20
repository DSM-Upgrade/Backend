package com.dsmupgrade.domain.homework.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HomeworkRepository extends JpaRepository<Homework, Integer> {
    Optional<Homework> findById(Integer integer);
    void deleteById(Integer integer);
}
