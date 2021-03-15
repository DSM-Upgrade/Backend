package com.dsmupgrade.domain.notification.service;

import com.dsmupgrade.domain.notification.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {
    List<NotificationResponse> getNotification();
    void deleteNotification(Integer id);
}
