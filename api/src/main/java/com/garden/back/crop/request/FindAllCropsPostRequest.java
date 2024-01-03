package com.garden.back.crop.request;

import com.garden.back.crop.service.request.FindAllCropsPostServiceRequest;
import com.garden.back.global.validation.EnumValue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record FindAllCropsPostRequest(
    @NotNull(message = "offset을 입력해 주세요") @PositiveOrZero(message = "0이상의 offset을 입력해주세요.")
    Integer offset,

    @NotNull(message = "limit을 입력해 주세요") @Positive(message = "0보다 큰 limit을 입력해 주세요.")
    Integer limit,

    @EnumValue(enumClass = FindAllCropsPostServiceRequest.OrderBy.class, message = "RECENT_DATE, LIKE_COUNT, OLDER_DATE 중 한개를 입력해주세요")
    String orderBy
){
    public FindAllCropsPostServiceRequest toServiceDto() {
        return new FindAllCropsPostServiceRequest(offset, limit, FindAllCropsPostServiceRequest.OrderBy.valueOf(orderBy));
    }
}
