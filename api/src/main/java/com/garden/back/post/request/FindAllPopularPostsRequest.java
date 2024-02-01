package com.garden.back.post.request;

import com.garden.back.post.domain.repository.request.FindAllPopularRepositoryPostsRequest;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record FindAllPopularPostsRequest(
    @PositiveOrZero(message = "0 이상의 정수만 입력해 주세요")
    Long offset,

    @Positive(message = "양수를 입력해 주세요.")
    Long limit,

    @Positive(message = "양수를 입력해 주세요.")
    Integer hour
) {
    public FindAllPopularRepositoryPostsRequest toRepositoryRequest() {
        return new FindAllPopularRepositoryPostsRequest(offset, limit, hour);
    }
}
