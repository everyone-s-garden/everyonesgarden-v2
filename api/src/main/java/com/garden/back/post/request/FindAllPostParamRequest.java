package com.garden.back.post.request;

import com.garden.back.global.validation.EnumValue;
import com.garden.back.post.domain.repository.request.FindAllPostParamRepositoryRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record FindAllPostParamRequest(
    @NotNull(message = "offset을 입력해 주세요") @PositiveOrZero(message = "0이상의 offset을 입력해주세요.")
    Integer offset,

    @NotNull(message = "limit을 입력해 주세요") @Positive(message = "0보다 큰 limit을 입력해 주세요.")
    Integer limit,

    String searchContent,

    @EnumValue(enumClass = FindAllPostParamRepositoryRequest.OrderBy.class, message = "COMMENT_COUNT, RECENT_DATE, LIKE_COUNT, OLDER_DATE 중 한개를 입력해주세요")
    String orderBy

) {
    public FindAllPostParamRepositoryRequest toRepositoryDto() {
        return new FindAllPostParamRepositoryRequest(offset, limit, searchContent, FindAllPostParamRepositoryRequest.OrderBy.valueOf(orderBy));
    }

}
