package com.garden.back.notification.domain

data class Notification(
    val title: String? = null,
    val summary: String? = null,
    val content: String,
    val recipient: Any,
)
