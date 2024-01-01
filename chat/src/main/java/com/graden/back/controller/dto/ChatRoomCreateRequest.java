package com.graden.back.controller.dto;

import com.graden.back.domain.ChatType;
import com.graden.back.global.loginuser.LoginUser;
import com.graden.back.global.validation.EnumValue;
import com.graden.back.service.dto.request.ChatRoomCreateParam;
import jakarta.validation.constraints.Positive;

public record ChatRoomCreateRequest(
        @Positive(message = "게시글 작성자의 아이디는 0이거나 음수일 수 없습니다.")
        Long writerId,

        @Positive(message = "게시글의 아이디는 0이거나 음수일 수 없습니다.")
        Long postId,

        @EnumValue(enumClass = ChatType.class, message = "채팅 타입은 GARDEN(텃밭 분양)이거나 TRADE(작물거래)여야 합니다.")
        String chatType
) {

    public ChatRoomCreateParam to(LoginUser loginUser) {
        return new ChatRoomCreateParam(
                writerId,
                loginUser.memberId(),
                postId,
                ChatType.valueOf(chatType)
        );
    }
}
