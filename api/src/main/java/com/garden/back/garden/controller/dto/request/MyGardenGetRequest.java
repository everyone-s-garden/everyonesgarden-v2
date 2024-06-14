package com.garden.back.garden.controller.dto.request;

import com.garden.back.garden.service.dto.request.MyGardenGetParam;
import com.garden.back.global.loginuser.LoginUser;

public record MyGardenGetRequest(
    Long memberId,
    Long nextGardenId
) {
    public static MyGardenGetParam toMyGardenGetParam(
        LoginUser loginUser,
        Long nextGardenId) {
        return new MyGardenGetParam(
            loginUser.memberId(),
            nextGardenId
        );
    }
}
