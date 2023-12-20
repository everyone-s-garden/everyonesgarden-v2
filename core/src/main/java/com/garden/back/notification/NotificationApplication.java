package com.garden.back.notification;

import com.garden.back.notification.domain.Notification;
import com.garden.back.notification.domain.NotificationType;
import com.garden.back.notification.service.EmailJavaMailNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotificationApplication {

    private final EmailJavaMailNotificationService javaMailService;

    public void send(Notification notification, NotificationType type) {
        switch (type) {
            case EMAIL -> {
                javaMailService.send(notification);
            }

            case SMS -> {
                throw new IllegalStateException("SMS Notification Not Yet Implemented");
            }

            case SMARTPHONE_PUSH -> {
                throw new IllegalStateException("Smartphone Push Notification Not Yet Implemented");
            }
        }
    }
}
