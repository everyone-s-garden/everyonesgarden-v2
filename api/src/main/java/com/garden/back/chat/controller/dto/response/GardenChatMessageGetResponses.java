package com.garden.back.chat.controller.dto.response;

import com.garden.back.service.garden.dto.response.GardenChatMessagesGetResults;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;

public record GardenChatMessageGetResponses(
        Slice<GardenChatMessageGetResponse> gardenChatMessageResponses
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
                        .map(GardenChatMessageGetResponse::to)
        );
    }
}
