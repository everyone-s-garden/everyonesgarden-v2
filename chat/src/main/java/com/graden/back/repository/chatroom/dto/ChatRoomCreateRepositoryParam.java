package com.graden.back.repository.chatroom.dto;

import com.graden.back.domain.ChatType;

public record ChatRoomCreateRepositoryParam(
        Long postId,
        Long writerId,
        ChatType chatType
) {
}
