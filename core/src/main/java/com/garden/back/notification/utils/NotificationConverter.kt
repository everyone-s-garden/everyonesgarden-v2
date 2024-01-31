package com.garden.back.notification.utils

import com.garden.back.notification.domain.Notification
import com.garden.back.notification.domain.NotificationType
import com.garden.back.notification.entity.NotificationEntity

object NotificationConverter {
    fun toDomain(notification: NotificationEntity): Notification {
        return Notification(
            id = notification.id,
            title = notification.title,
            content = notification.content,
            summary = notification.summary,
            recipient = notification.recipient,
            isRead = notification.isRead,
        )
    }

    fun toEntity(
        memberId: Long,
        type: NotificationType,
        domain: Notification,
    ): NotificationEntity {
        return NotificationEntity(
            id = domain.id,
            title = domain.title ?: "",
            content = domain.content,
            summary = domain.summary,
            recipient = domain.recipient as? String ?: "", // FIXME
            isRead = domain.isRead,
            type = type,
            memberId = memberId,
        )
    }
}