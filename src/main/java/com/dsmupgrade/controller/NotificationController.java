//package com.dsmupgrade.controller;
//
//import com.dsmupgrade.dto.request.NoticeResponse;
//import com.dsmupgrade.service.notification.NotificationService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/notification")
//@RequiredArgsConstructor
//public class NotificationController {
//    private final NotificationService notificationService;
//    @GetMapping("{id}")
//    public NoticeResponse getNotice(@PathVariable int id){
//        return notificationService.getNotice(id);
//    }
//}
