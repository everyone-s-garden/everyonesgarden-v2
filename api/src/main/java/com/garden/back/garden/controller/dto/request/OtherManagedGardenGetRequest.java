package com.garden.back.garden.controller.dto.request;

import com.garden.back.garden.service.dto.request.OtherManagedGardenGetParam;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record OtherManagedGardenGetRequest(
    @Positive(message = "member Id는 음수이거나 0일 수 없습니다.")
    Long otherMemberIdToVisit,

    @PositiveOrZero(message = "넥스트 키는 음수일 수 없습니다.")
    Long nextManagedGardenId
) {
    public OtherManagedGardenGetParam toOtherManagedGardenGetParam() {
        return new OtherManagedGardenGetParam(otherMemberIdToVisit, nextManagedGardenId);
    }
}
