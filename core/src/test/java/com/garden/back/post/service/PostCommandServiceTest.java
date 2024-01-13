package com.garden.back.post.service;

import com.garden.back.global.IntegrationTestSupport;
import com.garden.back.post.domain.Post;
import com.garden.back.post.domain.PostComment;
import com.garden.back.post.domain.repository.CommentLikeRepository;
import com.garden.back.post.domain.repository.PostCommentRepository;
import com.garden.back.post.domain.repository.PostLikeRepository;
import com.garden.back.post.domain.repository.PostRepository;
import com.garden.back.post.service.request.CommentCreateServiceRequest;
import com.garden.back.post.service.request.CommentUpdateServiceRequest;
import com.garden.back.post.service.request.PostCreateServiceRequest;
import com.garden.back.post.service.request.PostUpdateServiceRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@Transactional
class PostCommandServiceTest extends IntegrationTestSupport {

    @Autowired
    PostCommandService postCommandService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostCommentRepository postCommentRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    @Autowired
    CommentLikeRepository commentLikeRepository;

    @DisplayName("게시글을 생성한다.")
    @Test
    void createPost() {
        //given
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        given(imageUploader.upload(any(), any())).willReturn(expectedUrl);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
            "images",
            "image1.png",
            "image/png",
            "image-files".getBytes()
        );

        String expectedTitle = "제목";
        String expectedContent = "내용";

        PostCreateServiceRequest request = sut.giveMeBuilder(PostCreateServiceRequest.class)
            .set("title", expectedTitle)
            .set("content", expectedContent)
            .size("images", 1)
            .set("images[0]", mockMultipartFile)
            .sample();

        Long memberId = 1L;

        //when
        Long savedPostId = postCommandService.createPost(request, memberId);

        //then
        Post savedPost = postRepository.findById(savedPostId).orElseThrow(() -> new AssertionError("게시글 조회 실패"));

        assertThat(savedPost)
            .extracting("title", "content")
            .containsExactly("제목", "내용");
    }

    @DisplayName("게시글을 수정한다.")
    @Test
    void updatePost() {
        //given
        Long memberId = 1L;
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        Post post = Post.create("asdf", "asdf", memberId, List.of(expectedUrl));
        Long savedPostId = postRepository.save(post).getId();

        given(imageUploader.upload(any(), any())).willReturn(expectedUrl);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
            "images",
            "image1.png",
            "image/png",
            "image-files".getBytes()
        );

        String expectedTitle = "제목";
        String expectedContent = "내용";

        PostUpdateServiceRequest request = new PostUpdateServiceRequest(List.of(mockMultipartFile), expectedTitle, expectedContent, List.of(expectedUrl));
        //when
        postCommandService.updatePost(savedPostId, memberId, request);

        //then
        Post savedPost = postRepository.findById(savedPostId).orElseThrow(() -> new AssertionError("게시글 조회 실패"));
        assertThat(savedPost)
            .extracting("title", "content")
            .containsExactly(expectedTitle, expectedContent);
    }

    @DisplayName("게시글을 삭제한다.")
    @Test
    void deletePost() {
        //given
        Long memberId = 1L;
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        Post post = Post.create("asdf", "asdf", memberId, List.of(expectedUrl));
        Long savedPostId = postRepository.save(post).getId();

        //when
        postCommandService.deletePost(savedPostId, memberId);

        //then
        assertThat(postRepository.findById(savedPostId)).isEmpty();
    }

    @DisplayName("자신이 작성하지 않은 게시글을 삭제할 수 없다.")
    @Test
    void deletePostInvalid() {
        //given
        Long memberId = 1L;
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        Post post = Post.create("asdf", "asdf", memberId, List.of(expectedUrl));
        Long savedPostId = postRepository.save(post).getId();

        //when & then
        assertThrows(IllegalArgumentException.class, () -> {
            postCommandService.deletePost(savedPostId, 2L);
        }, "게시글의 작성자만 게시글을 삭제할 수 있습니다.");

    }

    @DisplayName("게시글에 좋아요를 누른다.")
    @Test
    void addLikeToPost() {
        //given
        Long memberId = 1L;
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        Post post = Post.create("asdf", "asdf", memberId, List.of(expectedUrl));
        Long savedPostId = postRepository.save(post).getId();
        Long expectedLikeCount = 1L;

        //when
        postCommandService.addLikeToPost(savedPostId, memberId);

        //then
        Post savedPost = postRepository.findById(savedPostId).orElseThrow(() -> new AssertionError("게시글 조회 실패"));
        assertThat(savedPost.getLikesCount()).isEqualTo(expectedLikeCount);
    }

    @DisplayName("게시글에 좋아요를 두 번 누를 수 없다.")
    @Test
    void addLikeToPostInvalid() {
        //given
        Long memberId = 1L;
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        Post post = Post.create("asdf", "asdf", memberId, List.of(expectedUrl));
        Long savedPostId = postRepository.save(post).getId();
        Long expectedLikeCount = 1L;

        //when & then
        postCommandService.addLikeToPost(savedPostId, memberId);

        assertThrows(IllegalArgumentException.class, () -> {
            postCommandService.addLikeToPost(savedPostId, memberId);
        }, "동일한 사용자는 같은 게시글에 좋아요를 누를 수 없습니다.");

        Post savedPost = postRepository.findById(savedPostId).orElseThrow(() -> new AssertionError("게시글 조회 실패"));
        assertThat(savedPost.getLikesCount()).isEqualTo(expectedLikeCount);
    }

    @DisplayName("게시글에 댓글을 작성한다.")
    @Test
    void createComment() {
        //given
        Long memberId = 1L;
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        Post post = Post.create("asdf", "asdf", memberId, List.of(expectedUrl));
        Long savedPostId = postRepository.save(post).getId();
        PostComment postComment = PostComment.create(null, memberId, "댓글내용", savedPostId);
        Long savedParentId = postCommentRepository.save(postComment).getId();
        String expectedCommentContent = "자식 댓글";
        CommentCreateServiceRequest request = new CommentCreateServiceRequest(expectedCommentContent, savedParentId);

        //when
        Long savedCommentId = postCommandService.createComment(savedPostId, memberId, request);

        //then
        PostComment savedComment = postCommentRepository.findById(savedCommentId).orElseThrow(() -> new AssertionError("댓글 조회 실패"));

        assertThat(savedComment)
            .extracting("parentCommentId", "content")
            .containsExactly(savedParentId, expectedCommentContent);
    }

    @DisplayName("대댓글에는 댓글을 작성할 수 없다.")
    @Test
    void createCommentInvalid() {
        //given
        Long memberId = 1L;
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        Post post = Post.create("asdf", "asdf", memberId, List.of(expectedUrl));
        Long savedPostId = postRepository.save(post).getId();
        PostComment postComment = PostComment.create(null, memberId, "댓글내용", savedPostId);
        Long savedParentId = postCommentRepository.save(postComment).getId();
        String expectedCommentContent = "자식 댓글";
        CommentCreateServiceRequest request = new CommentCreateServiceRequest(expectedCommentContent, savedParentId);

        //when & then
        Long childCommentId = postCommandService.createComment(savedPostId, memberId, request);
        request = new CommentCreateServiceRequest(expectedCommentContent, childCommentId);
        CommentCreateServiceRequest finalRequest = request;

        assertThrows(IllegalArgumentException.class, () -> {
            postCommandService.createComment(savedPostId, memberId, finalRequest);
        }, "대댓글에는 대댓글을 작성할 수 없습니다.");

    }

    @DisplayName("댓글을 수정한다.")
    @Test
    void updateComment() {
        //given
        Long memberId = 1L;
        PostComment postComment = PostComment.create(null, memberId, "댓글내용", 1L);
        Long savedCommentId = postCommentRepository.save(postComment).getId();
        String expectedCommentContent = "댓글 수정";
        CommentUpdateServiceRequest request = new CommentUpdateServiceRequest(expectedCommentContent);

        //when
        postCommandService.updateComment(savedCommentId, memberId, request);

        //then
        PostComment savedComment = postCommentRepository.findById(savedCommentId).orElseThrow(() -> new AssertionError("댓글 조회 실패"));
        assertThat(savedComment)
            .extracting("parentCommentId", "content")
            .containsExactly(null, expectedCommentContent);
    }

    @DisplayName("자신이 작성하지 않은 댓글은 수정할 수 없다.")
    @Test
    void updateCommentInvalid() {
        //given
        Long memberId = 1L;
        PostComment postComment = PostComment.create(null, memberId, "댓글내용", 1L);
        Long savedCommentId = postCommentRepository.save(postComment).getId();
        String expectedCommentContent = "댓글 수정";
        CommentUpdateServiceRequest request = new CommentUpdateServiceRequest(expectedCommentContent);

        //when & then
        assertThrows(IllegalArgumentException.class, () -> {
            postCommandService.updateComment(savedCommentId, 2L, request);
        }, "댓글 작성자에게 권한이 있습니다.");
    }

    @DisplayName("댓글을 삭제한다.")
    @Test
    void deleteComment() {
        //given
        Long memberId = 1L;
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        Post post = Post.create("asdf", "asdf", memberId, List.of(expectedUrl));
        Long savedPostId = postRepository.save(post).getId();

        CommentCreateServiceRequest request = new CommentCreateServiceRequest("제목", null);
        Long savedCommentId = postCommandService.createComment(savedPostId, memberId, request);

        //when
        postCommandService.deleteComment(savedCommentId, memberId);

        //then
        assertThat(postCommentRepository.findById(savedCommentId)).isEmpty();
    }

    @DisplayName("자신이 작성하지 않은 댓글은 삭제할 수 없다.")
    @Test
    void deleteCommentInvalid() {
        //given
        Long memberId = 1L;
        PostComment postComment = PostComment.create(null, memberId, "댓글내용", 1L);
        Long savedCommentId = postCommentRepository.save(postComment).getId();
        //when & then
        assertThrows(IllegalArgumentException.class, () -> {
            postCommandService.deleteComment(savedCommentId, 2L);
        }, "댓글 작성자에게 권한이 있습니다.");
    }

    @DisplayName("댓글에 좋아요를 누른다.")
    @Test
    void addLikeToComment() {
        //given
        Long memberId = 1L;

        PostComment postComment = PostComment.create(null, memberId, "댓글내용", 1L);
        Long savedCommentId = postCommentRepository.save(postComment).getId();

        //when
        postCommandService.addLikeToComment(savedCommentId, memberId);

        //then
        PostComment savedComment = postCommentRepository.findById(savedCommentId).orElseThrow(() -> new AssertionError("게시글 조회 실패"));
        assertThat(savedComment.getLikesCount()).isEqualTo(1L);
        assertThat(commentLikeRepository.existsCommentLikeByLikesClickerIdAndCommentId(memberId, savedCommentId)).isTrue();
    }

    @DisplayName("동일한 댓글에 같은 사용자가 좋아요를 누를 수 없다.")
    @Test
    void addLikeToCommentInvalid() {
        //given
        Long memberId = 1L;

        PostComment postComment = PostComment.create(null, memberId, "댓글내용", 1L);
        Long savedCommentId = postCommentRepository.save(postComment).getId();

        //when & then
        postCommandService.addLikeToComment(savedCommentId, memberId);
        assertThrows(IllegalArgumentException.class, () -> {
            postCommandService.addLikeToComment(savedCommentId, memberId);
        }, "동일한 사용자는 같은 댓글에 좋아요를 누를 수 없습니다.");
    }

    @DisplayName("댓글의 좋아요를 취소한다.")
    @Test
    void deleteCommentLike() {
        //given
        Long memberId = 1L;
        Long postId = 1L;
        PostComment postComment = PostComment.create(null, memberId, "댓글내용", postId);
        Long savedCommentId = postCommentRepository.save(postComment).getId();
        postCommandService.addLikeToComment(savedCommentId, memberId);

        //when
        postCommandService.deleteCommentLike(savedCommentId, memberId);

        //then
        PostComment savedComment = postCommentRepository.findById(savedCommentId).orElseThrow(() -> new AssertionError("게시글 조회 실패"));
        assertThat(savedComment.getLikesCount()).isZero();
        assertThat(postLikeRepository.existsPostLikeByLikesClickerIdAndPostId(memberId, postId)).isFalse();
    }

    @DisplayName("자신이 좋아요를 누른 댓글만 취소할 수 있다.")
    @Test
    void deleteCommentLikeInvalid() {
        //given
        Long memberId = 1L;
        Long postId = 1L;
        PostComment postComment = PostComment.create(null, memberId, "댓글내용", postId);
        Long savedCommentId = postCommentRepository.save(postComment).getId();
        postCommandService.addLikeToComment(savedCommentId, memberId);

        //when & then
        assertThrows(IllegalArgumentException.class, () -> {
            postCommandService.deleteCommentLike(savedCommentId, 2L);
        }, "해당 회원이 해당 댓글에 누른 좋아요가 없습니다.");
    }

    @DisplayName("게시글의 좋아요를 취소한다.")
    @Test
    void deletePostLike() {
        //given
        Long memberId = 1L;
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        Post post = Post.create("asdf", "asdf", memberId, List.of(expectedUrl));
        Long savedPostId = postRepository.save(post).getId();

        //when
        postCommandService.addLikeToPost(savedPostId, memberId);
        postCommandService.deletePostLike(savedPostId, memberId);

        //then
        Post savedPost = postRepository.findById(savedPostId).orElseThrow(() -> new AssertionError("게시글 조회 실패"));
        assertThat(postLikeRepository.findPostLikeByPostIdAndLikesClickerId(savedPostId, memberId)).isEmpty();
        assertThat(savedPost.getLikesCount()).isZero();
    }

    @DisplayName("자신이 누른 게시글 좋아요만 취소할 수 있다.")
    @Test
    void deletePostLikeInvalid() {
        //given
        Long memberId = 1L;
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        Post post = Post.create("asdf", "asdf", memberId, List.of(expectedUrl));
        Long savedPostId = postRepository.save(post).getId();

        //when & then
        assertThrows(IllegalArgumentException.class, () -> {
            postCommandService.deletePostLike(savedPostId, 2L);
        }, "해당 회원이 해당 게시글에 누른 좋아요가 없습니다.");
    }
}