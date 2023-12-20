package com.garden.back.notification.service

import com.garden.back.notification.domain.Notification
import com.garden.back.notification.utils.EmailUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class EmailJavaNotificationService(
    @Value("example@gmail.com")
    private var OFFICIAL_EMAIL: String,

    private val javaMailSender: JavaMailSender,
) : NotificationService {

    // TODO : Guarantee at least once delivery by retry
    @Async
    override fun send(notification: Notification) {
        if (EmailUtils.isEmailAddress(notification.recipient)) {
            throw IllegalStateException("Recipient must be an email address")
        }

        val message = SimpleMailMessage().apply {
            setTo(notification.recipient.toString())
            from = OFFICIAL_EMAIL
            subject = notification.title
            text = notification.content
        }

        javaMailSender.send(message)
    }
}
