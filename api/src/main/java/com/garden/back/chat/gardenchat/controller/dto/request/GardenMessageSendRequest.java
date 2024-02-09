package com.garden.back.chat.gardenchat.controller.dto.request;

import com.garden.back.garden.repository.chatentry.SessionId;
import com.garden.back.garden.service.dto.request.GardenChatMessageSendParam;

public record GardenMessageSendRequest(
    String content
) {
    public GardenChatMessageSendParam to(
        String memberId,
        Long roomId,
        String sessionId) {
        return new GardenChatMessageSendParam(
            SessionId.of(sessionId),
            Long.parseLong(memberId),
            roomId,
            content
        );
    }
}
