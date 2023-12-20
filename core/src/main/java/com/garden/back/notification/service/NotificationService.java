package com.garden.back.notification.service;

import com.garden.back.notification.domain.Notification;

public interface NotificationService {
    void send(Notification notification);
}
