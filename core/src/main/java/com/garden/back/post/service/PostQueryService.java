package com.garden.back.post.service;

import com.garden.back.post.domain.repository.PostQueryRepository;
import com.garden.back.post.domain.repository.request.FindAllPostCommentsParamRepositoryRequest;
import com.garden.back.post.domain.repository.request.FindAllPostParamRepositoryRequest;
import com.garden.back.post.domain.repository.response.FindAllPostsResponse;
import com.garden.back.post.domain.repository.response.FindPostDetailsResponse;
import com.garden.back.post.domain.repository.response.FindPostsAllCommentResponse;
import org.springframework.stereotype.Service;

@Service
public class PostQueryService {

    private final PostQueryRepository postQueryRepository;

    public PostQueryService(PostQueryRepository postQueryRepository) {
        this.postQueryRepository = postQueryRepository;
    }

    public FindPostDetailsResponse findPostById(Long id) {
        return postQueryRepository.findPostDetails(id);
    }

    public FindAllPostsResponse findAllPosts(FindAllPostParamRepositoryRequest request) {
        return postQueryRepository.findAllPosts(request);
    }

    public FindPostsAllCommentResponse findAllCommentsByPostId(Long id, FindAllPostCommentsParamRepositoryRequest request) {
        return postQueryRepository.findPostsAllComments(id, request);
    }

}
