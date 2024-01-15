package com.garden.back.chat.controller.dto.request;

import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.service.garden.dto.request.GardenChatRoomCreateParam;
import jakarta.validation.constraints.Positive;

public record GardenChatRoomCreateRequest(
        @Positive(message = "게시글 작성자의 아이디는 0이거나 음수일 수 없습니다.")
        Long writerId,

        @Positive(message = "게시글의 아이디는 0이거나 음수일 수 없습니다.")
        Long postId
) {

    public GardenChatRoomCreateParam to(LoginUser loginUser) {
        return new GardenChatRoomCreateParam(
                writerId,
                loginUser.memberId(),
                postId
        );
    }
}
