package com.garden.back.service.dto.request;

import com.garden.back.domain.garden.GardenChatRoom;
import com.garden.back.domain.garden.GardenChatRoomInfo;
import com.garden.back.repository.chatroom.dto.ChatRoomCreateRepositoryParam;

public record GardenChatRoomCreateParam(
        Long writerId,
        Long viewerId,
        Long postId
) {
    public GardenChatRoom toChatRoom() {
        return GardenChatRoom.of();
    }

    public GardenChatRoomInfo toChatRoomInfoToWriter() {
        return GardenChatRoomInfo.of(
                true,
                postId,
                writerId
        );
    }

    public GardenChatRoomInfo toChatRoomInfoToViewer() {
        return GardenChatRoomInfo.of(
                false,
                postId,
                viewerId
        );
    }

    public ChatRoomCreateRepositoryParam toChatRoomCreateRepositoryParam() {
        return new ChatRoomCreateRepositoryParam(
                postId,
                writerId
        );
    }
}
