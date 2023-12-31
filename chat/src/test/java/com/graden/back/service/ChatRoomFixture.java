package com.graden.back.service;

import com.graden.back.domain.ChatType;
import com.graden.back.service.dto.request.ChatRoomCreateParam;

public class ChatRoomFixture {

    public static ChatRoomCreateParam chatRoomCreateParam() {
        return new ChatRoomCreateParam(
                1L,
                2L,
                1L,
                ChatType.GARDEN
        );
    }

}
