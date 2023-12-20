package com.garden.back.notification

import com.garden.back.notification.domain.Notification
import com.garden.back.notification.domain.NotificationType
import com.garden.back.notification.service.EmailJavaNotificationService
import org.springframework.stereotype.Component

@Component
class NotificationApplication(
    private val javaMailSender: EmailJavaNotificationService,
) {
    fun send(notification: Notification, type: NotificationType) {
        when (type) {
            NotificationType.EMAIL -> javaMailSender.send(notification)
            NotificationType.SMS -> TODO("SMS Notification Not Yet Implemented")
            NotificationType.SMARTPHONE_PUSH -> TODO("Smartphone Push Notification Not Yet Implemented")
        }
    }
}
