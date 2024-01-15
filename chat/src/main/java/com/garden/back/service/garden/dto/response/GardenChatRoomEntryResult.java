package com.garden.back.service.garden.dto.response;

import com.garden.back.repository.chatroominfo.garden.dto.GardenChatRoomEnterRepositoryResponse;

public record GardenChatRoomEntryResult(
        Long partnerId,
        Long postId
) {
    public static GardenChatRoomEntryResult to(GardenChatRoomEnterRepositoryResponse response) {
        return new GardenChatRoomEntryResult(
                response.getMemberId(),
                response.getPostId()
        );
    }

}
