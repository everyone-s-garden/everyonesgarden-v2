package com.garden.back.notification

import com.garden.back.notification.domain.Notification
import com.garden.back.notification.entity.NotificationRepository
import com.garden.back.notification.utils.NotificationConverter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class NotificationReadApplication(
    private val notificationRepository: NotificationRepository,
) {
    fun markAsRead(
        memberId: Long,
        notificationId: Long,
    ): Notification {
        val notificationFound = notificationRepository
            .findByIdOrNull(notificationId)
            ?: throw ResponseStatusException(HttpStatus.NO_CONTENT)

        if (!notificationFound.isAuthorized(memberId)) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN)
        }

        return notificationFound
            .markAsRead()
            .let { notificationRepository.save(it) }
            .let { NotificationConverter.toDomain(it) }
    }
}
