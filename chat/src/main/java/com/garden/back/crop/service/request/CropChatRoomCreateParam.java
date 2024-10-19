package com.garden.back.crop.service.request;

import com.garden.back.crop.domain.CropChatRoom;
import com.garden.back.crop.domain.CropChatRoomInfo;
import com.garden.back.garden.repository.chatroom.dto.ChatRoomCreateRepositoryParam;

public record CropChatRoomCreateParam(
        Long writerId,
        Long viewerId,
        Long postId
) {
    public CropChatRoom toChatRoom() {
        return CropChatRoom.of(1L);
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
                viewerId
        );
    }
}
