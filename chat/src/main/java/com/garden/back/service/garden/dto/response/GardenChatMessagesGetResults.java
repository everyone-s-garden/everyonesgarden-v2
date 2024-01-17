package com.garden.back.service.garden.dto.response;


import com.garden.back.domain.garden.GardenChatMessage;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public record GardenChatMessagesGetResults(
        Slice<GardenChatMessagesGetResult> gardenChatMessagesGetResponses

) {
    public record GardenChatMessagesGetResult(
            Long chatMessageId,
            Long memberId,
            String contents,
            LocalDateTime createdAt,
            boolean readOrNot,
            boolean isMine
    ) {
        public static GardenChatMessagesGetResult to(GardenChatMessage gardenChatMessage, Long requestMemberId) {
            return new GardenChatMessagesGetResult(
                    gardenChatMessage.getChatMessageId(),
                    gardenChatMessage.getMemberId(),
                    gardenChatMessage.getContents(),
                    gardenChatMessage.getCreatedAt(),
                    gardenChatMessage.isReadOrNot(),
                    Objects.equals(gardenChatMessage.getMemberId(), requestMemberId)
            );
        }
    }

    public static GardenChatMessagesGetResults to(Slice<GardenChatMessage> gardenChatMessages, Long requestMemberId) {
        List<GardenChatMessagesGetResult> sortedResponses = gardenChatMessages.stream()
                .map(gardenChatMessage -> GardenChatMessagesGetResult.to(gardenChatMessage, requestMemberId))
                .sorted(Comparator.comparing(GardenChatMessagesGetResult::createdAt))
                .toList();

        return new GardenChatMessagesGetResults(new SliceImpl<>(
                sortedResponses,
                gardenChatMessages.getPageable(),
                gardenChatMessages.hasNext()));
    }
}
