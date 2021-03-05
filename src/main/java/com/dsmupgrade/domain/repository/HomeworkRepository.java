package com.dsmupgrade.domain.repository;

import com.dsmupgrade.domain.entity.homework.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HomeworkRepository extends JpaRepository<Homework, Integer> {
    Optional<Homework> findById(Integer integer);
}
