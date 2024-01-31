package com.garden.back.notification.domain

data class Notification(
    val id: Long = 0,
    val title: String? = null,
    val summary: String? = null,
    val content: String,
    val recipient: Any,
    val isRead: Boolean = false,
)
