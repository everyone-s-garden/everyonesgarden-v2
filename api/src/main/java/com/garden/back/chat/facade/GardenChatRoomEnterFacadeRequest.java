package com.garden.back.chat.facade;

import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.service.garden.dto.request.GardenChatRoomEntryParam;

public record GardenChatRoomEnterFacadeRequest(
        Long roomId,
        Long memberId
) {
    public GardenChatRoomEntryParam to() {
        return new GardenChatRoomEntryParam(
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
