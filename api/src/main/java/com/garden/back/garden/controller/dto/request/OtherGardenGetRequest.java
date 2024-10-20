package com.garden.back.garden.controller.dto.request;

import com.garden.back.garden.facade.dto.OtherGardenGetFacadeRequest;
import com.garden.back.garden.service.dto.request.OtherGardenGetParam;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record OtherGardenGetRequest(
    @Positive(message = "member Id는 음수이거나 0일 수 없습니다.")
    Long otherMemberIdToVisit,

    @PositiveOrZero(message = "넥스트 키는 음수일 수 없습니다.")
    Long nextManagedGardenId
) {

    public OtherGardenGetFacadeRequest toOtherGardenGetParam(Long myMemberId) {
        return new OtherGardenGetFacadeRequest(
            otherMemberIdToVisit,
            nextManagedGardenId,
            myMemberId
        );
    }
}
