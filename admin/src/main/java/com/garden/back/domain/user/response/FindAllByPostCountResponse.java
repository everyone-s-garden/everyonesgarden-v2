package com.garden.back.domain.user.response;

import java.util.List;

public record FindAllByPostCountResponse(
    List<Result> results
) {
    public record Result(
        Long id,
        Long count
    ) {
        public static Result from(Object[] objects) {
            Long memberId = ((Number) objects[0]).longValue();
            Long postCount = ((Number) objects[1]).longValue();

            return new Result(memberId, postCount);
        }
    }

    public static FindAllByPostCountResponse from(List<Object[]> results) {
        return new FindAllByPostCountResponse(results.stream().map(Result::from).toList());
    }
}
