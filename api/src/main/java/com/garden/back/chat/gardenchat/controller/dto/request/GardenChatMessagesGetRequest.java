package com.garden.back.chat.gardenchat.controller.dto.request;

import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.garden.service.dto.request.GardenChatMessagesGetParam;

public record GardenChatMessagesGetRequest(
        Long memberId,
        Long chatRoomId,
        int pageNumber
) {
    public static GardenChatMessagesGetParam to(
            LoginUser loginUser,
            Long chatRoomId,
            int pageNumber
    ){
        return new GardenChatMessagesGetParam(
          loginUser.memberId(),
          chatRoomId,
          pageNumber
        );
    }
}
