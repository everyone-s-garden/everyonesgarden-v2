package com.garden.back.notification

import com.garden.back.notification.domain.Notification
import com.garden.back.notification.entity.NotificationRepository
import com.garden.back.notification.utils.NotificationConverter
import org.springframework.stereotype.Component

@Component
class NotificationGetApplication(
    private val notificationRepository: NotificationRepository,
) {
    fun getAll(memberId: Long): List<Notification> {
        return notificationRepository
            .findAllByMemberId(memberId)
            .map { NotificationConverter.toDomain(it) }
    }

    fun getAllUnread(memberId: Long): List<Notification> {
        return notificationRepository
            .findAllUnread(memberId)
            .map { NotificationConverter.toDomain(it) }
    }

    fun pollUnreadCount(memberId: Long): Int {
        return notificationRepository
            .findAllUnread(memberId)
            .count()
    }
}