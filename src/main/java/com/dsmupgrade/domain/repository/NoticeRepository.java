package com.dsmupgrade.domain.repository;

import com.dsmupgrade.domain.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice,Integer> {

}
