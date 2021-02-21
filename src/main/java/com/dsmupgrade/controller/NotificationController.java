package com.dsmupgrade.controller;

import com.dsmupgrade.dto.response.NotificationResponse;
import com.dsmupgrade.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationResponse> getNotification(){
        return notificationService.getNotification();
    }
    @DeleteMapping("{id}")
    public void deleteNotification(@PathVariable Integer id){
        notificationService.deleteNotification(id);
    }
}
