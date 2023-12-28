package com.garden.back.post.service.request;

public record FindAllPostParamServiceRequest(
    Integer offset,
    Integer limit,
    OrderBy orderBy
) {

    public enum OrderBy {
        COMMENT_COUNT, RECENT_DATE, LIKE_COUNT, OLDER_DATE
    }
}
