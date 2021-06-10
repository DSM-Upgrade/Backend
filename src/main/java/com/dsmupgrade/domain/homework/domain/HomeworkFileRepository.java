package com.dsmupgrade.domain.homework.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface HomeworkFileRepository extends JpaRepository<HomeworkFile, HomeworkFilePk> {
    List<HomeworkFile> findByIdHomeworkIdAndIdUsername(int id, String username);
    void deleteByIdHomeworkIdAndIdUsername(int id, String username);
}
