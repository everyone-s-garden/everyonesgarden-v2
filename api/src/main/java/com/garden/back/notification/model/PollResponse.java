package com.garden.back.notification.model;

public record PollResponse(
    int count
) {
    public static PollResponse of(int count) {
        return new PollResponse(count);
    }
}
