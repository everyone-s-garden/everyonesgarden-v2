package com.garden.back.service.dto.request;

import com.garden.back.repository.chatroominfo.garden.dto.GardenChatRoomEnterRepositoryResponse;

public record ChatRoomEntryResult(
        Long partnerId,
        Long postId
) {
    public static ChatRoomEntryResult to(GardenChatRoomEnterRepositoryResponse response) {
        return new ChatRoomEntryResult(
                response.getMemberId(),
                response.getPostId()
        );
    }

}
