package com.garden.back.post.request;

import com.garden.back.global.validation.EnumValue;
import com.garden.back.post.domain.repository.request.FindAllPostCommentsParamRepositoryRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record FindAllCommentsParamRequest(
    @NotNull(message = "offset을 입력해 주세요") @PositiveOrZero(message = "0이상의 offset을 입력해주세요.")
    Integer offset,

    @NotNull(message = "limit을 입력해 주세요") @Positive(message = "0보다 큰 limit을 입력해 주세요.")
    Integer limit,

    @EnumValue(enumClass = FindAllPostCommentsParamRepositoryRequest.OrderBy.class, message = "RECENT_DATE, LIKE_COUNT, OLDER_DATE 중 한개를 입력해주세요")
    String orderBy
) {

    public FindAllPostCommentsParamRepositoryRequest toRepositoryDto() {
        return new FindAllPostCommentsParamRepositoryRequest(offset, limit, FindAllPostCommentsParamRepositoryRequest.OrderBy.valueOf(orderBy));
    }
}
