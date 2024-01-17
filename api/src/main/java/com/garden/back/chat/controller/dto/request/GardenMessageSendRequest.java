package com.garden.back.chat.controller.dto.request;

import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.repository.chatentry.SessionId;
import com.garden.back.service.garden.dto.request.GardenChatMessageSendParam;

public record GardenMessageSendRequest(
        SessionId sessionId,
        String content
) {
    public GardenChatMessageSendParam to(LoginUser loginUser, Long roomId) {
        return new GardenChatMessageSendParam(
                sessionId,
                loginUser.memberId(),
                roomId,
                content
        );
    }
}
