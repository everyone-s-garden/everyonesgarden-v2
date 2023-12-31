package com.graden.back.service.dto.request;

import com.graden.back.domain.ChatRoom;
import com.graden.back.domain.ChatRoomInfo;
import com.graden.back.domain.ChatType;
import com.graden.back.repository.chatroom.dto.ChatRoomCreateRepositoryParam;

public record ChatRoomCreateParam(
        Long writerId,
        Long viewerId,
        Long postId,
        ChatType chatType
) {
    public ChatRoom toChatRoom() {
        return ChatRoom.to();
    }

    public ChatRoomInfo toChatRoomInfoToWriter() {
        return ChatRoomInfo.of(
                true,
                postId,
                chatType,
                writerId
        );
    }

    public ChatRoomInfo toChatRoomInfoToViewer() {
        return ChatRoomInfo.of(
                false,
                postId,
                chatType,
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
