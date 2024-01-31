package com.garden.back.notification

import com.garden.back.notification.domain.Notification
import com.garden.back.notification.domain.NotificationType
import com.garden.back.notification.domain.slack.SlackChannel
import com.garden.back.notification.entity.NotificationRepository
import com.garden.back.notification.service.NotificationServiceFactory
import com.garden.back.notification.utils.NotificationConverter
import org.springframework.stereotype.Component

@Component
class NotificationSentApplication(
    private val notificationServiceFactory: NotificationServiceFactory,
    private val notificationRepository: NotificationRepository,
) {
    fun toEmail(
        memberId: Long,
        recipient: String,
        title: String,
        content: String,
    ) {
        val notification = Notification(
            title = title,
            content = content,
            recipient = recipient,
        )

        NotificationConverter
            .toEntity(
                memberId = memberId,
                type = NotificationType.EMAIL,
                domain = notification,
            )
            .let { notificationRepository.save(it) }

        notificationServiceFactory
            .get(NotificationType.EMAIL)
            .send(notification)
    }

    fun toSlack(
        recipient: SlackChannel,
        message: String,
    ) {
        val notification = Notification(
            content = message,
            recipient = recipient,
        )

        notificationServiceFactory
            .get(NotificationType.SLACK)
            .send(notification)
    }
}
