package com.garden.back.notification.domain;

public record Notification(
    String title,
    String content,
    String summary,
    Object recipient
) {}
