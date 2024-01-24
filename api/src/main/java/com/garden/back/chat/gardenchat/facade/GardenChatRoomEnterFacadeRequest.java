package com.garden.back.chat.gardenchat.facade;

import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.garden.service.dto.request.GardenChatRoomEntryParam;

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
