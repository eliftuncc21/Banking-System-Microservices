package org.eliftunc.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.eliftunc.notificationservice.dto.NotificationResponse;
import org.eliftunc.notificationservice.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/list-notification")
    public Page<NotificationResponse> getAllNotifications(@RequestParam int page, @RequestParam int size){
        return notificationService.getNotification(page, size);
    }

    @GetMapping("/notification/{id}")
    public NotificationResponse findNotificationById(@PathVariable Long id){
        return notificationService.getNotificationById(id);
    }
}
