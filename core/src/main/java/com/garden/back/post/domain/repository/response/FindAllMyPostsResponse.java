package com.garden.back.post.domain.repository.response;

import java.util.List;

/**
 * author id로 조회할 때 응답 dto로 쓰이기도 함
 * @param postInfos
 */
public record FindAllMyPostsResponse(
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
