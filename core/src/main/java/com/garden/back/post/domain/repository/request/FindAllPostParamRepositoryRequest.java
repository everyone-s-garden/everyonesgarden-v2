package com.garden.back.post.domain.repository.request;

import com.garden.back.post.domain.PostType;

public record FindAllPostParamRepositoryRequest(
    Integer offset,
    Integer limit,
    String searchContent,
    PostType postType,
    OrderBy orderBy
) {

    public enum OrderBy {
        COMMENT_COUNT, RECENT_DATE, LIKE_COUNT, OLDER_DATE
    }
}
