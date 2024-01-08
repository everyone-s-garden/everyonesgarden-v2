package com.garden.back.post.domain.repository.request;

public record FindAllPostCommentsParamRepositoryRequest(
    Integer offset,
    Integer limit,
    OrderBy orderBy
) {
    public enum OrderBy {
        RECENT_DATE, LIKE_COUNT, OLDER_DATE
    }
}