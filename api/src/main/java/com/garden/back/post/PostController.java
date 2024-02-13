package com.garden.back.post;

import com.garden.back.global.LocationBuilder;
import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.post.domain.repository.response.FindAllPopularPostsResponse;
import com.garden.back.post.domain.repository.response.FindAllPostsResponse;
import com.garden.back.post.domain.repository.response.FindPostDetailsResponse;
import com.garden.back.post.domain.repository.response.FindPostsAllCommentResponse;
import com.garden.back.post.request.*;
import com.garden.back.post.service.PostCommandService;
import com.garden.back.post.service.PostQueryService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/posts")
public class PostController {

    private final PostQueryService postQueryService;
    private final PostCommandService postCommandService;

    public PostController(
        PostQueryService postQueryService,
        PostCommandService postCommandService
    ) {
        this.postQueryService = postQueryService;
        this.postCommandService = postCommandService;
    }

    //게시글 상세 조회
    @GetMapping(path = "/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FindPostDetailsResponse> findPostDetails(
        @PathVariable("postId") Long postId,
        @CurrentUser LoginUser loginUser
    ) {
        return ResponseEntity.ok(postQueryService.findPostById(postId, loginUser.memberId()));
    }

    //게시글 전체 조회
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FindAllPostsResponse> findAllPosts(
        @ModelAttribute @Valid FindAllPostParamRequest request
    ) {
        return ResponseEntity.ok(postQueryService.findAllPosts(request.toRepositoryDto()));
    }

    //게시글 댓글 조회
    @GetMapping(value = "/{postId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FindPostsAllCommentResponse> findPostsAllComment(
        @PathVariable("postId") Long postId,
        @ModelAttribute @Valid FindAllCommentsParamRequest request,
        @CurrentUser LoginUser loginUser
    ) {
        return ResponseEntity.ok(postQueryService.findAllCommentsByPostId(postId, loginUser.memberId(), request.toRepositoryDto()));
    }

    //게시글 생성
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createPost(
        @RequestPart(value = "texts", required = true) @Valid PostCreateRequest request,
        @RequestPart(value = "images", required = false) List<MultipartFile> images,
        @CurrentUser LoginUser loginUser
    ) {
        URI location = LocationBuilder.buildLocation(
            postCommandService.createPost(request.toServiceRequest(images), loginUser.memberId())
        );
        return ResponseEntity.created(location).build();
    }

    //게시글 좋아요
    @PostMapping(path = "/{postId}/likes")
    public ResponseEntity<Void> createPostLikes(
        @PathVariable("postId") Long postId,
        @CurrentUser LoginUser loginUser
    ) {
        URI location = LocationBuilder.buildLocation(
            postCommandService.addLikeToPost(postId, loginUser.memberId())
        );

        return ResponseEntity.created(location).build();
    }

    //게시글 좋아요 취소
    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<Void> deletePostLikes(
        @PathVariable("postId") Long postId,
        @CurrentUser LoginUser loginUser
    ) {
        postCommandService.deletePostLike(postId, loginUser.memberId());
        return ResponseEntity.noContent().build();
    }

    //게시글 수정
    @PatchMapping(path = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updatePost(
        @RequestPart(value = "texts", required = true) @Valid PostUpdateRequest request,
        @RequestPart(value = "images", required = false) List<MultipartFile> images,
        @PathVariable("postId") Long postId,
        @CurrentUser LoginUser loginUser
    ) {
        postCommandService.updatePost(postId, loginUser.memberId(), request.toServiceDto(images));
        return ResponseEntity.ok().build();
    }

    //게시글 삭제
    @DeleteMapping(path = "/{postId}")
    public ResponseEntity<Void> deletePost(
        @PathVariable("postId") Long postId,
        @CurrentUser LoginUser loginUser
    ) {
        postCommandService.deletePost(postId, loginUser.memberId());
        return ResponseEntity.noContent().build();
    }

    //댓글 작성
    @PostMapping(path = "/{postId}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createComment(
        @PathVariable("postId") Long postId,
        @RequestBody @Valid CommentCreateRequest request,
        @CurrentUser LoginUser loginUser
    ) {
        URI location = LocationBuilder.buildLocation(
            postCommandService.createComment(postId, loginUser.memberId(), request.toServiceDto())
        );
        return ResponseEntity.created(location).build();
    }

    //댓글 좋아요
    @PostMapping("/comments/{commentId}/likes")
    public ResponseEntity<Void> createCommentLikes(
        @PathVariable("commentId") Long commentId,
        @CurrentUser LoginUser loginUser
    ) {
        URI location = LocationBuilder.buildLocation(
            postCommandService.addLikeToComment(commentId, loginUser.memberId())
        );

        return ResponseEntity.created(location).build();
    }

    //댓글 좋아요 취소
    @DeleteMapping("/comments/{commentId}/likes")
    public ResponseEntity<Void> cancelCommentLikes(
        @PathVariable("commentId") Long commentId,
        @CurrentUser LoginUser loginUser
    ) {
        postCommandService.deleteCommentLike(commentId, loginUser.memberId());
        return ResponseEntity.noContent().build();
    }

    //댓글 수정
    @PatchMapping(path = "/comments/{commentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateComment(
        @PathVariable("commentId") Long commentId,
        @RequestBody @Valid CommentUpdateRequest request,
        @CurrentUser LoginUser loginUser
    ) {
        postCommandService.updateComment(commentId, loginUser.memberId(), request.toServiceRequest());
        return ResponseEntity.ok().build();
    }

    //댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
        @PathVariable("commentId") Long commentId,
        @CurrentUser LoginUser loginUser
    ) {
        postCommandService.deleteComment(commentId, loginUser.memberId());
        return ResponseEntity.noContent().build();
    }

    //실시간 인기 게시글 조회
    @GetMapping(value = "/popular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FindAllPopularPostsResponse> getPopularPosts(
        @ModelAttribute @Valid FindAllPopularPostsRequest request
    ) {
        return ResponseEntity.ok(postQueryService.findAllPopularPosts(request.toRepositoryRequest()));
    }
}
