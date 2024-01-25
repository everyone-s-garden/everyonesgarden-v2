package com.garden.back.notification.service

import com.garden.back.notification.domain.Notification
import com.garden.back.notification.domain.NotificationType

interface NotificationService {
    fun send(notification: Notification)
    fun supports(type: NotificationType): Boolean
}
