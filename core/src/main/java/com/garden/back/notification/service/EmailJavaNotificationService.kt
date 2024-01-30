package com.garden.back.notification.service

import com.garden.back.notification.domain.Notification
import com.garden.back.notification.domain.NotificationType
import com.garden.back.notification.utils.EmailUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Profile("!test")
@Service("email")
open class EmailJavaNotificationService(
    @Value("\${spring.mail.username}")
    private var OFFICIAL_EMAIL: String,

    private val javaMailSender: JavaMailSender,
) : NotificationService {

    // TODO : Guarantee at least once delivery by retry
    @Async
    override fun send(notification: Notification) {
        if (!EmailUtils.isValid(notification.recipient)) {
            throw IllegalStateException("Recipient must be an email address")
        }

        val message = SimpleMailMessage().apply {
            setTo(notification.recipient.toString())
            from = OFFICIAL_EMAIL
            subject = notification.title
            text = notification.content
        }

        try {
            javaMailSender.send(message)
        } catch (e: Exception) {
            // TODO - add logging
            throw IllegalStateException(e.message)
        }
    }

    override fun supports(type: NotificationType): Boolean {
        return type == NotificationType.EMAIL
    }
}
