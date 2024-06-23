package com.garden.back.post.domain.repository.response;

import java.util.List;

public record FindAllMyLikePostsResponse(
    List<PostInfo> postInfos
) {
    public record PostInfo(
        Long postId,
        String title,
        String preview,
        String content,
        Long likesCount,
        Long commentsCount,
        UserResponse userInfo
    ) {}
}
