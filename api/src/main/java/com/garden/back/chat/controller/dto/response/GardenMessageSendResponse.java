package com.garden.back.chat.controller.dto.response;

import com.garden.back.service.garden.dto.response.GardenChatMessageSendResult;

import java.time.LocalDateTime;

public record GardenMessageSendResponse(
        Long chatMessageId,
        Long memberId,
        Long chatRoomId,
        String message,
        boolean readOrNot,
        LocalDateTime createdAt
) {
    public static GardenMessageSendResponse to(GardenChatMessageSendResult result) {
        return new GardenMessageSendResponse(
                result.chatMessageId(),
                result.memberId(),
                result.chatRoomId(),
                result.message(),
                result.readOrNot(),
                result.createdAt()
        );
    }
}
