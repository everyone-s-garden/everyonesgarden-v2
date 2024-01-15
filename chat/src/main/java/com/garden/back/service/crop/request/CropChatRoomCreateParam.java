package com.garden.back.service.crop.request;

import com.garden.back.domain.crop.CropChatRoom;
import com.garden.back.domain.crop.CropChatRoomInfo;
import com.garden.back.repository.chatroom.dto.ChatRoomCreateRepositoryParam;

public record CropChatRoomCreateParam(
        Long writerId,
        Long viewerId,
        Long postId
) {
    public CropChatRoom toChatRoom() {
        return CropChatRoom.of();
    }

    public CropChatRoomInfo toChatRoomInfoToWriter() {
        return CropChatRoomInfo.of(
                true,
                postId,
                writerId
        );
    }

    public CropChatRoomInfo toChatRoomInfoToViewer() {
        return CropChatRoomInfo.of(
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
