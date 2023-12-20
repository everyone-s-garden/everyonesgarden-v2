package com.garden.back.notification.domain

data class Notification(
    val title: String,
    val content: String,
    val summary: String?,
    val recipient: Any,
)
