package com.garden.back.post.domain.repository;

import com.garden.back.post.domain.repository.request.*;
import com.garden.back.post.domain.repository.response.*;

public interface PostQueryRepository {
    FindPostDetailsResponse findPostDetails(Long id);

    FindAllPostsResponse findAllPosts(FindAllPostParamRepositoryRequest request);

    FindPostsAllCommentResponse findPostsAllComments(Long id, FindAllPostCommentsParamRepositoryRequest request);

    FindAllMyLikePostsResponse findAllByMyLike(Long loginUserId, FindAllMyLikePostsRepositoryRequest request);

    FindAllMyPostsResponse findAllMyPosts(Long loginUserId, FindAllMyPostsRepositoryRequest request);

    FindAllMyCommentPostsResponse findAllByMyComment(Long loginUserId, FindAllMyCommentPostsRepositoryRequest request);

    FindAllPopularPostsResponse findAllPopularPosts(FindAllPopularRepositoryPostsRequest request);
}
