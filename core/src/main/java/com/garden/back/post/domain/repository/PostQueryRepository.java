package com.garden.back.post.domain.repository;

import com.garden.back.post.domain.repository.request.*;
import com.garden.back.post.domain.repository.response.*;

public interface PostQueryRepository {
    FindPostDetailsResponse findPostDetails(Long id, Long loginUserId);

    FindAllPostsResponse findAllPosts(FindAllPostParamRepositoryRequest request);

    FindPostsAllCommentResponse findPostsAllComments(Long id, Long loginUserId, FindAllPostCommentsParamRepositoryRequest request);

    FindAllMyLikePostsResponse findAllByMyLike(Long loginUserId, FindAllMyLikePostsRepositoryRequest request);

    FindAllMyPostsResponse findAllMyPosts(Long loginUserId, FindAllMyPostsRepositoryRequest request);

    FindAllMyCommentPostsResponse findAllByMyComment(Long loginUserId, FindAllMyCommentPostsRepositoryRequest request);

    FindAllPopularPostsResponse findAllPopularPosts(FindAllPopularRepositoryPostsRequest request);
}
