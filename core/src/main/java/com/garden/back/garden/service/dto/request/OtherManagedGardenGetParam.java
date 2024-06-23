package com.garden.back.garden.service.dto.request;

public record OtherManagedGardenGetParam(
    Long otherMemberId,
    Long nextManagedGardenId
) {
    public MyManagedGardenGetParam toMyManagedGardenGetParam() {
        return new MyManagedGardenGetParam(otherMemberId, nextManagedGardenId);
    }
}
