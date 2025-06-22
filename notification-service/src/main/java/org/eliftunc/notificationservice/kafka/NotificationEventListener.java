package org.eliftunc.notificationservice.kafka;

import lombok.RequiredArgsConstructor;
import org.eliftunc.events.UserCreatedEvent;
import org.eliftunc.notificationservice.dto.NotificationMessage;
import org.eliftunc.notificationservice.entity.Notification;
import org.eliftunc.notificationservice.mapper.NotificationMapper;
import org.eliftunc.notificationservice.repository.NotificationRepository;
import org.eliftunc.notificationservice.service.MailService;
import org.eliftunc.notificationservice.utils.NotificationMessageBuilder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationRepository notificationRepository;
    private final MailService mailSender;
    private final NotificationMapper notificationMapper;

    @KafkaListener(topics = "user-service", groupId = "notification-service-group",
            containerFactory = "kafkaListenerContainerFactory")
    public void notificationCreated(UserCreatedEvent event) {
        Notification notification = notificationMapper.toUserNotification(event);

        NotificationMessage message = NotificationMessageBuilder.buildUserCreatedMessage(event);

        handleNotificationAndSend(notification, message);
    }

    private void handleNotificationAndSend(Notification notification, NotificationMessage message) {
        notification.setSubject(message.getSubject());
        notification.setMessage(message.getBody());

        mailSender.sendSimpleMail(
                notification.getEmail(),
                notification.getSubject(),
                notification.getMessage()
        );

        notificationRepository.save(notification);
    }
}
