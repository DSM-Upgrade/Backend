package com.dsmupgrade.domain.homework.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkFileRepository extends JpaRepository<HomeworkFile, HomeworkFilePk> {
    List<HomeworkFile> findByIdHomeworkIdAndUsername(int id, String username);
    void deleteByIdHomeworkIdAndUsername(int id, String username);
}
