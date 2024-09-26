package com.garden.back.garden.service.dto.request;

public record OtherGardenGetParam(
    Long otherMemberId,
    Long nextGardenId,
    Long myMemberId
) {
}
