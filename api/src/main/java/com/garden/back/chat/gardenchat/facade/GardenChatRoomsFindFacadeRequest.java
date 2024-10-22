package com.garden.back.chat.gardenchat.facade;

import com.garden.back.garden.service.dto.request.GardenChatRoomsFindParam;
import com.garden.back.global.loginuser.LoginUser;

public record GardenChatRoomsFindFacadeRequest(
        Long memberId,
        Integer pageNumber
) {
    public GardenChatRoomsFindParam toGardenChatRoomsFindParam() {
        return new GardenChatRoomsFindParam(
                memberId,
                pageNumber
        );
    }

    public static GardenChatRoomsFindFacadeRequest of(
            LoginUser loginUser,
            Integer pageNumber
    ) {
        return new GardenChatRoomsFindFacadeRequest(
                loginUser.memberId(),
                pageNumber
        );
    }
}
