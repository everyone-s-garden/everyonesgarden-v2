package com.garden.back.notification

import com.garden.back.notification.domain.Notification
import com.garden.back.notification.domain.slack.SlackChannel
import com.garden.back.notification.service.NotificationService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class NotificationApplication(
    @Qualifier("email")
    private val javaMailService: NotificationService,

    @Qualifier("slack")
    private val slackService: NotificationService,
) {

    fun toEmail(
        recipient: String,
        title: String,
        content: String,
    ) {
        val notification = Notification(
            title = title,
            content = content,
            recipient = recipient,
        )

        javaMailService.send(notification)
    }

    fun toSlack(
        recipient: SlackChannel,
        message: String,
    ) {
        val notification = Notification(
            content = message,
            recipient = recipient,
        )

        slackService.send(notification)
    }
}
