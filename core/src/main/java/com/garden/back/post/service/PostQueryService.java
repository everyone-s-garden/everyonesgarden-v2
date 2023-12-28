package com.garden.back.post.service;

import com.garden.back.post.FindAllPostsResponse;
import com.garden.back.post.FindPostDetailsResponse;
import com.garden.back.post.FindPostsAllCommentResponse;
import com.garden.back.post.service.request.FindAllPostParamServiceRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostQueryService {

    public FindPostDetailsResponse findPostById(Long id) {
        return new FindPostDetailsResponse(1L, "작성자", "내용", "제목");
    }

    public FindAllPostsResponse findAllPosts(FindAllPostParamServiceRequest request) {
        return new FindAllPostsResponse(List.of(new FindAllPostsResponse.PostInfo(1L, "제목", 2L, 3L)));
    }

    public FindPostsAllCommentResponse findAllCommentsByPostId(Long id) {
        return new FindPostsAllCommentResponse(List.of(new FindPostsAllCommentResponse.CommentInfo(2L, 1L, 0L, "대댓글", "작성자2"), new FindPostsAllCommentResponse.CommentInfo(1L, null, 0L, "댓글", "작성자1")));
    }

}
