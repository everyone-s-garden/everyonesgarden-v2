package com.garden.back.chat.cropchat.controller.dto.request;

import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.crop.service.request.CropChatRoomCreateParam;
import jakarta.validation.constraints.Positive;

public record CropChatRoomCreateRequest(
        @Positive(message = "게시글 작성자의 아이디는 0이거나 음수일 수 없습니다.")
        Long writerId,

        @Positive(message = "게시글의 아이디는 0이거나 음수일 수 없습니다.")
        Long postId
) {

    public CropChatRoomCreateParam to(LoginUser loginUser) {
        return new CropChatRoomCreateParam(
                writerId,
                loginUser.memberId(),
                postId
        );
    }
}
