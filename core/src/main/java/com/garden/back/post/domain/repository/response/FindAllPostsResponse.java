package com.garden.back.post.domain.repository.response;

import java.util.List;

public record FindAllPostsResponse(
    List<PostInfo> postInfos
) {
    public record PostInfo(
       Long postId,
       String title,
       Long likeCount,
       Long commentCount
    ) {}
}