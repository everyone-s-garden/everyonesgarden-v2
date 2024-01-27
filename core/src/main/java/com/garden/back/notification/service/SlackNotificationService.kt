package com.garden.back.notification.service

import com.garden.back.notification.domain.Notification
import com.garden.back.notification.domain.NotificationType
import com.garden.back.notification.domain.slack.SlackChannel
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service("slack")
open class SlackNotificationService(
    private val restTemplate: RestTemplate,

    @Value("\$api.slack.bot")
    private val slackHookUrlOfBot: String,
) : NotificationService {

    @Async
    override fun send(notification: Notification) {
        val recipientChannel = notification.recipient as? SlackChannel
            ?: throw IllegalStateException("Invalid request for SlackNotificationService : recipient must be type of SlackChannel, but found ${notification.recipient}")

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }

        val body = mapOf(
            "text" to notification.content,
        )

        try {
            restTemplate.postForEntity(
                getHookUrl(recipientChannel),
                HttpEntity(body, headers),
                String::class.java,
            )
        } catch (e: Exception) {
            // TODO - logging
            throw IllegalStateException("Failed to send message to slack (${recipientChannel.displayName}) using a hook of ${getHookUrl(recipientChannel)}")
        }
    }

    private fun getHookUrl(channel: SlackChannel): String {
        return when (channel) {
            SlackChannel.BOT -> slackHookUrlOfBot
        }
    }

    override fun supports(type: NotificationType): Boolean {
        return type == NotificationType.SLACK
    }
}
