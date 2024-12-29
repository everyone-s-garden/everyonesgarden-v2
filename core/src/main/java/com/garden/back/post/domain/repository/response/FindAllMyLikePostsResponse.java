package com.garden.back.post.domain.repository.response;

import com.garden.back.post.domain.PostType;

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
        PostType postType,
        UserResponse userInfo
    ) {}
}
