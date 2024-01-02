package com.garden.back.controller.dto;

import com.garden.back.domain.ChatType;
import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.global.validation.EnumValue;
import com.garden.back.service.dto.request.GardenChatRoomCreateParam;
import jakarta.validation.constraints.Positive;

public record GardenChatRoomCreateRequest(
        @Positive(message = "게시글 작성자의 아이디는 0이거나 음수일 수 없습니다.")
        Long writerId,

        @Positive(message = "게시글의 아이디는 0이거나 음수일 수 없습니다.")
        Long postId,

        @EnumValue(enumClass = ChatType.class, message = "채팅 타입은 GARDEN(텃밭 분양)이거나 TRADE(작물거래)여야 합니다.")
        String chatType
) {

    public GardenChatRoomCreateParam to(LoginUser loginUser) {
        return new GardenChatRoomCreateParam(
                writerId,
                loginUser.memberId(),
                postId,
                ChatType.valueOf(chatType)
        );
    }
}
