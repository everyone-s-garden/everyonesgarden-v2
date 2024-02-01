package com.garden.back.notification.model;

import com.garden.back.notification.domain.Notification;

public record GetNotificationResponse(
        String title,
        String content,
        Boolean isRead
) {
    public static GetNotificationResponse of(Notification notification) {
        return new GetNotificationResponse(
                notification.getTitle(),
                notification.getContent(),
                notification.isRead()
        );
    }
}
