package org.eliftunc.notificationservice.mapper;

import org.eliftunc.events.UserCreatedEvent;
import org.eliftunc.notificationservice.dto.NotificationResponse;
import org.eliftunc.notificationservice.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mapping(target = "fullName", expression = "java(event.getFirstName() + \" \" + event.getLastName())")
    Notification toUserNotification(UserCreatedEvent event);

    NotificationResponse toNotificationResponse(Notification notification);
}
