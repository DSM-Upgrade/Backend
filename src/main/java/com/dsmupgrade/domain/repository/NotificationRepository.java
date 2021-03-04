package com.dsmupgrade.domain.repository;

import com.dsmupgrade.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
}
