package com.garden.back.post.domain.repository;

import com.garden.back.post.domain.repository.request.FindAllPostCommentsParamRepositoryRequest;
import com.garden.back.post.domain.repository.request.FindAllPostParamRepositoryRequest;
import com.garden.back.post.domain.repository.response.FindAllPostsResponse;
import com.garden.back.post.domain.repository.response.FindPostDetailsResponse;
import com.garden.back.post.domain.repository.response.FindPostsAllCommentResponse;

public interface PostQueryRepository {
    FindPostDetailsResponse findPostDetails(Long id);

    FindAllPostsResponse findAllPosts(FindAllPostParamRepositoryRequest request);

    FindPostsAllCommentResponse findPostsAllComments(Long id, FindAllPostCommentsParamRepositoryRequest request);
}
