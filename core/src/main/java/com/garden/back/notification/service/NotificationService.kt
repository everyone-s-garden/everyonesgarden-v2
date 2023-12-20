package com.garden.back.notification.service

import com.garden.back.notification.domain.Notification

interface NotificationService {
    fun send(notification: Notification)
}
