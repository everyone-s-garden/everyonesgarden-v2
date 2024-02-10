package com.garden.back.chat.gardenchat.controller.dto.response;

import com.garden.back.garden.service.dto.response.GardenChatMessageSendResult;

import java.time.LocalDateTime;

public record GardenMessageSendResponse(
    Long chatMessageId,
    Long memberId,
    Long chatRoomId,
    String contents,
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
