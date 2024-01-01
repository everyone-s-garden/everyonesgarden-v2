package com.graden.back.docs;

import com.graden.back.controller.dto.ChatRoomCreateRequest;

public class ChatRoomFixture {

    public static ChatRoomCreateRequest chatRoomCreateRequest() {
        return new ChatRoomCreateRequest(
                1L,
                1L,
                "GARDEN"
        );
    }
}
