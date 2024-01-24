package com.garden.back.post;

import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.post.domain.repository.response.FindAllMyCommentPostsResponse;
import com.garden.back.post.domain.repository.response.FindAllMyLikePostsResponse;
import com.garden.back.post.domain.repository.response.FindAllMyPostsResponse;
import com.garden.back.post.request.FindAllMyCommentPostsRequest;
import com.garden.back.post.request.FindAllMyLikePostsRequest;
import com.garden.back.post.request.FindAllMyPostsRequest;
import com.garden.back.post.service.PostQueryService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/my/posts")
public class MyPostController {

    private final PostQueryService postQueryService;

    public MyPostController(PostQueryService postQueryService) {
        this.postQueryService = postQueryService;
    }

    //내가 작성한 댓글의 게시글
    @GetMapping(
        value = "/comments",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<FindAllMyCommentPostsResponse> findAllByComment(
        @CurrentUser LoginUser loginUser,
        @ModelAttribute @Valid FindAllMyCommentPostsRequest request
    ) {
        return ResponseEntity.ok(postQueryService.findAllByMyComment(loginUser.memberId(), request.toRepositoryDto()));
    }

    //내가 작성한 게시글
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FindAllMyPostsResponse> findAllMyPosts(
        @CurrentUser LoginUser loginUser,
        @ModelAttribute @Valid FindAllMyPostsRequest request
    ) {
        return ResponseEntity.ok(postQueryService.findAllMyPosts(loginUser.memberId(), request.toRepositoryDto()));
    }

    //내가 좋아요 한 게시글
    @GetMapping(
        value = "/likes",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<FindAllMyLikePostsResponse> findAllByLike(
        @CurrentUser LoginUser loginUser,
        @ModelAttribute @Valid FindAllMyLikePostsRequest request
    ) {
        return ResponseEntity.ok(postQueryService.findAllByMyLike(loginUser.memberId(), request.toRepositoryDto()));
    }
}
