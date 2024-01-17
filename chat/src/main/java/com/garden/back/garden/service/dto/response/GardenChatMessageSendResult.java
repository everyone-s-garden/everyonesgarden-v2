package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.domain.GardenChatMessage;

import java.time.LocalDateTime;

public record GardenChatMessageSendResult(

        Long chatMessageId,
        Long memberId,
        Long chatRoomId,
        String message,
        boolean readOrNot,
        LocalDateTime createdAt
) {

    public static GardenChatMessageSendResult to(GardenChatMessage gardenChatMessage) {
        return new GardenChatMessageSendResult(
                gardenChatMessage.getChatMessageId(),
                gardenChatMessage.getMemberId(),
                gardenChatMessage.getChatRoom().getChatRoomId(),
                gardenChatMessage.getContents(),
                gardenChatMessage.isReadOrNot(),
                gardenChatMessage.getCreatedAt()
        );
    }

}
