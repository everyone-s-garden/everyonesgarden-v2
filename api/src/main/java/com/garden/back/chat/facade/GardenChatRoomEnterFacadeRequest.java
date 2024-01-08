package com.garden.back.chat.facade;

import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.service.dto.request.ChatRoomEntryParam;

public record GardenChatRoomEnterFacadeRequest(
        Long roomId,
        Long memberId
) {
    public ChatRoomEntryParam to() {
        return new ChatRoomEntryParam(
                roomId,
                memberId
        );
    }

    public static GardenChatRoomEnterFacadeRequest to(
            Long roomId,
            LoginUser loginUser
    ) {
        return new GardenChatRoomEnterFacadeRequest(
                roomId,
                loginUser.memberId()
        );
    }

}
