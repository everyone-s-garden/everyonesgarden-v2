package com.garden.back.notification.service

import com.garden.back.notification.domain.NotificationType
import org.springframework.stereotype.Service

@Service
class NotificationServiceFactory(
    private val notificationServices: List<NotificationService>,
) {
    fun get(type: NotificationType): NotificationService {
        return notificationServices
            .firstOrNull { it.supports(type) }
            ?: TODO("notification of type $type is not yet supported")
    }
}
