package com.graden.back.service.dto.request;

import com.graden.back.domain.ChatType;
import com.graden.back.domain.garden.GardenChatRoom;
import com.graden.back.domain.garden.GardenChatRoomInfo;
import com.graden.back.repository.chatroom.dto.ChatRoomCreateRepositoryParam;

public record GardenChatRoomCreateParam(
        Long writerId,
        Long viewerId,
        Long postId,
        ChatType chatType
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
                writerId,
                chatType
        );
    }
}
