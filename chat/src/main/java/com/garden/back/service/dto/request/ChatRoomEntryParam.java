package com.garden.back.service.dto.request;

import com.garden.back.global.loginuser.LoginUser;

public record ChatRoomEntryParam (
        Long roomId,
        Long memberId
) {
    public static ChatRoomEntryParam to(
            Long roomId,
            LoginUser loginUser
    ){
        return new ChatRoomEntryParam(
                roomId,
                loginUser.memberId()
        );

    }
}
