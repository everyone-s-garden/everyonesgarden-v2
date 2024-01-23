package com.garden.back.post.request;

import com.garden.back.post.domain.repository.request.FindAllMyPostsRepositoryRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record FindAllMyPostsRequest(
    @PositiveOrZero(message = "0이상의 수를 입력해주세요.") @NotNull(message = "null값은 허용하지 않습니다.")
    Long offset,
    @Positive(message = "양수를 입력해주세요.") @NotNull(message = "null값은 허용하지 않습니다.")
    Long limit
) {
    public FindAllMyPostsRepositoryRequest toRepositoryDto() {
        return new FindAllMyPostsRepositoryRequest(offset, limit);
    }
}
