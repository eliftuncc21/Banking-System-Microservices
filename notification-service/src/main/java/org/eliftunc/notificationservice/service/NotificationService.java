package org.eliftunc.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.eliftunc.notificationservice.dto.NotificationResponse;
import org.eliftunc.notificationservice.mapper.NotificationMapper;
import org.eliftunc.notificationservice.repository.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public Page<NotificationResponse> getNotification(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return notificationRepository.findAll(pageable).map(notificationMapper::toNotificationResponse);
    }

    public NotificationResponse getNotificationById(Long id) {
        return notificationRepository.findById(id).map(notificationMapper::toNotificationResponse).orElse(null);
    }
}
