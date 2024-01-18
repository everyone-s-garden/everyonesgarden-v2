package com.garden.back.garden.service.dto.request;

import com.garden.back.global.loginuser.LoginUser;

public record GardenChatRoomEntryParam(
        Long roomId,
        Long memberId
) {
    public static GardenChatRoomEntryParam to(
            Long roomId,
            LoginUser loginUser
    ){
        return new GardenChatRoomEntryParam(
                roomId,
                loginUser.memberId()
        );

    }
}
