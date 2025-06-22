package org.eliftunc.notificationservice.utils;

import org.eliftunc.events.UserCreatedEvent;
import org.eliftunc.notificationservice.dto.NotificationMessage;

public class NotificationMessageBuilder {
    public static NotificationMessage buildUserCreatedMessage(UserCreatedEvent event) {
        String subject = "Kayıt başarılı!";
        String body = "Merhaba " + event.getFirstName()
                + "\n\nBankamıza kaydınız başarıyla tamamlandı.";
        return new NotificationMessage(subject, body);
    }

}
