package com.garden.back.post.domain.repository.request;

public record FindAllPostParamRepositoryRequest(
    Integer offset,
    Integer limit,
    String searchContent,
    OrderBy orderBy
) {

    public enum OrderBy {
        COMMENT_COUNT, RECENT_DATE, LIKE_COUNT, OLDER_DATE
    }
}
