package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.repository.chatmessage.ChatRoomFindRepositoryResponse;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record GardenChatMessageFindResults(
        List<GardenChatMessageFindResult> gardenChatMessageFindResults,
        boolean hasNest

) {
    public static GardenChatMessageFindResults to(Slice<ChatRoomFindRepositoryResponse> responses, Map<Long, String> messageById) {
        return new GardenChatMessageFindResults(
                responses.stream()
                        .map(chatRoomFindRepositoryResponse ->
                                GardenChatMessageFindResult.to(
                                        chatRoomFindRepositoryResponse,
                                        messageById.get(chatRoomFindRepositoryResponse.getChatMessageId())
                                )
                        ).toList(),
                responses.hasNext()
        );

    }

    public record GardenChatMessageFindResult(
            Long chatMessageId,
            LocalDateTime createdAt,
            int readNotCnt,
            Long partnerId,
            Long chatRoomId,
            Long postId,
            String recentContents
    ) {
        public static GardenChatMessageFindResult to(ChatRoomFindRepositoryResponse response, String recentContents) {
            return new GardenChatMessageFindResult(
                    response.getChatMessageId(),
                    response.getCreatedAt(),
                    response.getNotReadCount(),
                    response.getPartnerId(),
                    response.getChatRoomId(),
                    response.getPostId(),
                    recentContents
            );
        }

    }
}
