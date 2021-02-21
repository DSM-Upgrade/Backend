package com.dsmupgrade.service.notification;

import com.dsmupgrade.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {
    List<NotificationResponse> getNotification();
    void deleteNotification(Integer id);
}
