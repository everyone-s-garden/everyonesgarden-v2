package com.garden.back.notification.service

import com.garden.back.notification.domain.Notification
import com.garden.back.notification.domain.slack.SlackChannel
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service("slack")
open class SlackNotificationService(
    private val restTemplate: RestTemplate,
) : NotificationService {

    @Async
    override fun send(notification: Notification) {
        val recipientChannel = notification.recipient as? SlackChannel
            ?: throw IllegalStateException("Invalid request for SlackNotificationService : recipient must be type of SlackChannel, but found ${notification.recipient}")

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }

        val body = mapOf(
            "text" to "${notification.title} ${notification.content}",
        )

        try {
            restTemplate.postForEntity(
                recipientChannel.hookUrl,
                HttpEntity(body, headers),
                String::class.java,
            )
        } catch (e: Exception) {
            // TODO - logging
            throw IllegalStateException("Failed to send message to slack (${recipientChannel.displayName}) using a hook of ${recipientChannel.hookUrl}")
        }
    }
}
