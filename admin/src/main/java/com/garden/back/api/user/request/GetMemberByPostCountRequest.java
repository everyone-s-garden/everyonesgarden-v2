package com.garden.back.api.user.request;

import com.garden.back.domain.user.request.FindAllMemberByPostCountRequest;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record GetMemberByPostCountRequest(
    @PositiveOrZero(message = "0 이상의 수를 입력해 주세요.")
    Integer offset,

    @Positive(message = "양수를 입력해 주세요.")
    Integer limit
) {
    public FindAllMemberByPostCountRequest toServiceRequest() {
        return new FindAllMemberByPostCountRequest(offset, limit);
    }
}
