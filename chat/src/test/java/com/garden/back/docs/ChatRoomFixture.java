package com.garden.back.docs;

import com.garden.back.controller.dto.GardenChatRoomCreateRequest;

public class ChatRoomFixture {

    public static GardenChatRoomCreateRequest chatRoomCreateRequest() {
        return new GardenChatRoomCreateRequest(
                1L,
                1L,
                "GARDEN"
        );
    }
}
