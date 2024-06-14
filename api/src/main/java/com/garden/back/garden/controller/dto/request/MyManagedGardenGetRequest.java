package com.garden.back.garden.controller.dto.request;

import com.garden.back.garden.service.dto.request.MyManagedGardenGetParam;
import com.garden.back.global.loginuser.LoginUser;

public record MyManagedGardenGetRequest(
    Long memberId,
    Long nextManagedGardenId
) {
    public static MyManagedGardenGetParam toMyManagedGardenGetParam(
        LoginUser loginUser,
        Long nextManagedGardenId) {
        return new MyManagedGardenGetParam(
            loginUser.memberId(),
            nextManagedGardenId
        );
    }
}
