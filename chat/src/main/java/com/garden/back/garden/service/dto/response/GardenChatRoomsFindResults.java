package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.repository.chatmessage.ChatRoomFindRepositoryResponse;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record GardenChatRoomsFindResults(
        List<GardenChatRoomsFindResult> gardenChatRoomsFindResults,
        boolean hasNest

) {
    public static GardenChatRoomsFindResults to(Slice<ChatRoomFindRepositoryResponse> responses, Map<Long, String> messageById) {
        return new GardenChatRoomsFindResults(
                responses.stream()
                        .map(chatRoomFindRepositoryResponse ->
                                GardenChatRoomsFindResult.to(
                                        chatRoomFindRepositoryResponse,
                                        messageById.get(chatRoomFindRepositoryResponse.getChatMessageId())
                                )
                        ).toList(),
                responses.hasNext()
        );

    }

    public record GardenChatRoomsFindResult(
            Long chatMessageId,
            LocalDateTime createdAt,
            int readNotCnt,
            Long partnerId,
            Long chatRoomId,
            Long postId,
            String recentContents
    ) {
        public static GardenChatRoomsFindResult to(ChatRoomFindRepositoryResponse response, String recentContents) {
            return new GardenChatRoomsFindResult(
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
