package com.garden.back.crop.request;

import com.garden.back.crop.domain.CropCategory;
import com.garden.back.crop.domain.TradeType;
import com.garden.back.crop.domain.repository.request.FindAllCropsPostRepositoryRequest;
import com.garden.back.global.validation.EnumValue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record FindAllCropsPostRequest(
    @NotNull(message = "offset을 입력해 주세요") @PositiveOrZero(message = "0이상의 offset을 입력해주세요.")
    Integer offset,

    @NotNull(message = "limit을 입력해 주세요") @Positive(message = "0보다 큰 limit을 입력해 주세요.")
    Integer limit,

    String searchContent,

    TradeType tradeType,

    CropCategory cropCategory,

    String region,

    @PositiveOrZero(message = "가격은 0이상의 수로 입력해주세요.")
    Integer minPrice,

    @PositiveOrZero(message = "가격은 0이상의 수로 입력해주세요.")
    Integer maxPrice,

    @EnumValue(enumClass = FindAllCropsPostRepositoryRequest.OrderBy.class, message = "RECENT_DATE, LIKE_COUNT, OLDER_DATE, LOWER_PRICE, HIGHER_PRICE 중 한개를 입력해주세요")
    String orderBy
){
    public FindAllCropsPostRepositoryRequest toRepositoryDto() {
        return new FindAllCropsPostRepositoryRequest(
            offset,
            limit,
            searchContent,
            tradeType,
            cropCategory,
            region,
            minPrice,
            maxPrice,
            FindAllCropsPostRepositoryRequest.OrderBy.valueOf(orderBy)
        );
    }
}