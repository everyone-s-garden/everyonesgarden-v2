package com.garden.back.chat.controller.dto.request;

import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.repository.chatentry.SessionId;
import com.garden.back.service.garden.dto.request.GardenSessionCreateParam;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record GardenSessionCreateRequest(
        @NotBlank(message = "세션 아이디는 null이거나 빈 값일 수 없습니다.")
        String sessionId,

        @NotNull(message = "채팅방 아이디는 null일 수 없습니다.")
        @Positive(message = "채팅방 아이디는 양수여야 합니다.")
        Long roomId
) {

    public GardenSessionCreateParam to(LoginUser loginUser) {
        return new GardenSessionCreateParam(
                SessionId.of(sessionId),
                roomId,
                loginUser.memberId()
        );
    }
}
