package com.garden.back.notification

import com.garden.back.notification.domain.Notification
import com.garden.back.notification.domain.NotificationType
import com.garden.back.notification.service.EmailJavaNotificationService
import com.garden.back.notification.service.SlackNotificationService
import org.springframework.stereotype.Component

@Component
class NotificationApplication(
    private val javaMailService: EmailJavaNotificationService,
    private val slackService: SlackNotificationService,
) {
    fun send(notification: Notification, type: NotificationType) {
        when (type) {
            NotificationType.EMAIL -> javaMailService.send(notification)
            NotificationType.SMS -> TODO("SMS Notification Not Yet Implemented")
            NotificationType.SMARTPHONE_PUSH -> TODO("Smartphone Push Notification Not Yet Implemented")
            NotificationType.SLACK -> slackService.send(notification)
        }
    }
}
