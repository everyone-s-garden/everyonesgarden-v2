package com.garden.back.chat.controller.dto.response;

import com.garden.back.service.garden.dto.response.GardenChatMessagesGetResults;

import java.time.LocalDateTime;
import java.util.List;

public record GardenChatMessageGetResponses(
        List<GardenChatMessageGetResponse> gardenChatMessageResponses,
        boolean hasNext
) {
    public record GardenChatMessageGetResponse(
            Long chatMessageId,
            Long memberId,
            String contents,
            LocalDateTime createdAt,
            boolean readOrNot,
            boolean isMine
    ) {
        public static GardenChatMessageGetResponse to(GardenChatMessagesGetResults.GardenChatMessagesGetResult result) {
            return new GardenChatMessageGetResponse(
                    result.chatMessageId(),
                    result.memberId(),
                    result.contents(),
                    result.createdAt(),
                    result.readOrNot(),
                    result.isMine()
            );
        }

    }

    public static GardenChatMessageGetResponses to(GardenChatMessagesGetResults gardenChatMessagesGetResults) {
        return new GardenChatMessageGetResponses(
                gardenChatMessagesGetResults.gardenChatMessagesGetResponses()
                        .stream()
                        .map(GardenChatMessageGetResponse::to)
                        .toList(),
                gardenChatMessagesGetResults.hasNext()
        );
    }
}
