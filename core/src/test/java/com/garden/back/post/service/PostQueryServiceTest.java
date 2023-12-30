package com.garden.back.post.service;

import com.garden.back.global.IntegrationTestSupport;
import com.garden.back.member.Member;
import com.garden.back.member.MemberRepository;
import com.garden.back.member.Role;
import com.garden.back.post.domain.Post;
import com.garden.back.post.domain.PostComment;
import com.garden.back.post.domain.repository.PostCommentRepository;
import com.garden.back.post.domain.repository.PostRepository;
import com.garden.back.post.domain.repository.request.FindAllPostCommentsParamRepositoryRequest;
import com.garden.back.post.domain.repository.request.FindAllPostParamRepositoryRequest;
import com.garden.back.post.domain.repository.response.FindAllPostsResponse;
import com.garden.back.post.domain.repository.response.FindPostDetailsResponse;
import com.garden.back.post.domain.repository.response.FindPostsAllCommentResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class PostQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    PostQueryService postQueryService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostCommentRepository postCommentRepository;

    @DisplayName("게시글 id로 게시글의 상세를 조회할 수 있다.(댓글 수, 좋아요 수, 작성자, 제목, 내용)")
    @Test
    void findPostById() {
        //given
        String content = "내용";
        String nickname = "닉네임";
        String title = "제목";
        Member member = Member.create("asdf@example.com", nickname, Role.USER);
        Long savedMemberId = memberRepository.save(member).getId();
        Post post = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"));
        Long savedPostId = postRepository.save(post).getId();

        FindPostDetailsResponse response = new FindPostDetailsResponse(0L, 0L, nickname, content, title);

        //when & then
        assertThat(postQueryService.findPostById(savedPostId)).isEqualTo(response);
    }

    @DisplayName("모든 게시글을 댓글 순으로 정렬해서 조회할 수 있다.")
    @Test
    void findAllPostsOrderByComments() {
        //given
        String content = "내용";
        String nickname = "닉네임";
        String title = "제목";
        Member member = Member.create("asdf@example.com", nickname, Role.USER);
        Long savedMemberId = memberRepository.save(member).getId();

        Post post = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"));
        post.increaseLikeCount(); // 좋아요 수 많음, 더 최근 게시글
        Long savedPostId1 = postRepository.save(post).getId();

        Post post2 = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"));
        post2.increaseCommentCount(); //댓글 수 많음, 더 늦은 게시글
        Long savedPostId2 = postRepository.save(post2).getId();

        List<FindAllPostsResponse.PostInfo> postInfosForCommentCount = List.of(
            new FindAllPostsResponse.PostInfo(savedPostId2, title, post2.getLikesCount(), post2.getCommentsCount()),
            new FindAllPostsResponse.PostInfo(savedPostId1, title, post.getLikesCount(), post.getCommentsCount()) //Post2가 댓글 더 많음
        );

        FindAllPostParamRepositoryRequest request = new FindAllPostParamRepositoryRequest(0, 10, FindAllPostParamRepositoryRequest.OrderBy.COMMENT_COUNT);
        System.out.println(request);
        System.out.println(savedPostId1 + " ," + savedPostId2);
        System.out.println(postQueryService.findAllPosts(request));

        //when & then
        FindAllPostsResponse expectedResponseForCommentCount = new FindAllPostsResponse(postInfosForCommentCount);
        assertThat(postQueryService.findAllPosts(request)).isEqualTo(expectedResponseForCommentCount);
    }

    @DisplayName("모든 게시글을 좋아요 순으로 정렬해서 조회할 수 있다.")
    @Test
    void findAllPostsOrderByLikes() {
        //given
        String content = "내용";
        String nickname = "닉네임";
        String title = "제목";
        Member member = Member.create("asdf@example.com", nickname, Role.USER);
        Long savedMemberId = memberRepository.save(member).getId();

        Post post = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"));
        post.increaseLikeCount(); // 좋아요 수 많음, 더 최근 게시글
        Long savedPostId1 = postRepository.save(post).getId();

        Post post2 = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"));
        post2.increaseCommentCount(); //댓글 수 많음, 더 늦은 게시글
        Long savedPostId2 = postRepository.save(post2).getId();

        List<FindAllPostsResponse.PostInfo> postInfosForCommentCount = List.of(
            new FindAllPostsResponse.PostInfo(savedPostId1, title, post.getLikesCount(), post.getCommentsCount()), //Post가 댓글 더 많음
            new FindAllPostsResponse.PostInfo(savedPostId2, title, post2.getLikesCount(), post2.getCommentsCount())
        );

        FindAllPostParamRepositoryRequest request = new FindAllPostParamRepositoryRequest(0, 10, FindAllPostParamRepositoryRequest.OrderBy.LIKE_COUNT);

        //when & then
        FindAllPostsResponse expectedResponseForCommentCount = new FindAllPostsResponse(postInfosForCommentCount);
        assertThat(postQueryService.findAllPosts(request)).isEqualTo(expectedResponseForCommentCount);
    }

    @DisplayName("모든 게시글을 최신 생성일 순으로 정렬해서 조회할 수 있다.")
    @Test
    void findAllPostsOrderByRecent() {
        //given
        String content = "내용";
        String nickname = "닉네임";
        String title = "제목";
        Member member = Member.create("asdf@example.com", nickname, Role.USER);
        Long savedMemberId = memberRepository.save(member).getId();

        Post post = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"));
        post.increaseLikeCount(); // 좋아요 수 많음, 더 최근 게시글
        Long savedPostId1 = postRepository.save(post).getId();

        Post post2 = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"));
        post2.increaseCommentCount(); //댓글 수 많음, 더 늦은 게시글
        Long savedPostId2 = postRepository.save(post2).getId();

        List<FindAllPostsResponse.PostInfo> postInfosForCommentCount = List.of(
            new FindAllPostsResponse.PostInfo(savedPostId2, title, post2.getLikesCount(), post2.getCommentsCount()),
            new FindAllPostsResponse.PostInfo(savedPostId1, title, post.getLikesCount(), post.getCommentsCount()) //Post가 더 오래 됨

        );

        FindAllPostParamRepositoryRequest request = new FindAllPostParamRepositoryRequest(0, 10, FindAllPostParamRepositoryRequest.OrderBy.RECENT_DATE);

        //when & then
        FindAllPostsResponse expectedResponseForCommentCount = new FindAllPostsResponse(postInfosForCommentCount);
        assertThat(postQueryService.findAllPosts(request)).isEqualTo(expectedResponseForCommentCount);
    }

    @DisplayName("모든 게시글을 오래된 생성일 순으로 정렬해서 조회할 수 있다.")
    @Test
    void findAllPostsOrderByOlder() {
        //given
        String content = "내용";
        String nickname = "닉네임";
        String title = "제목";
        Member member = Member.create("asdf@example.com", nickname, Role.USER);
        Long savedMemberId = memberRepository.save(member).getId();

        Post post = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"));
        post.increaseLikeCount(); // 좋아요 수 많음, 더 최근 게시글
        Long savedPostId1 = postRepository.save(post).getId();

        Post post2 = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"));
        post2.increaseCommentCount(); //댓글 수 많음, 더 늦은 게시글
        Long savedPostId2 = postRepository.save(post2).getId();

        List<FindAllPostsResponse.PostInfo> postInfosForCommentCount = List.of(
            new FindAllPostsResponse.PostInfo(savedPostId1, title, post.getLikesCount(), post.getCommentsCount()), //Post가 더 먼저 생성 됨
            new FindAllPostsResponse.PostInfo(savedPostId2, title, post2.getLikesCount(), post2.getCommentsCount())
        );

        FindAllPostParamRepositoryRequest request = new FindAllPostParamRepositoryRequest(0, 10, FindAllPostParamRepositoryRequest.OrderBy.OLDER_DATE);

        //when & then
        FindAllPostsResponse expectedResponseForCommentCount = new FindAllPostsResponse(postInfosForCommentCount);
        assertThat(postQueryService.findAllPosts(request)).isEqualTo(expectedResponseForCommentCount);
    }

    @DisplayName("댓글을 최근 순으로 정렬해서 조회할 수 있다.")
    @Test
    void findAllCommentsByPostIdOrderByOlderDate() {
        //given
        String content = "내용";
        String nickname = "닉네임";
        String title = "제목";
        Member member = Member.create("asdf@example.com", nickname, Role.USER);
        Long savedMemberId = memberRepository.save(member).getId();

        Post post = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"));
        Long savedPostId = postRepository.save(post).getId();
        PostComment postComment = PostComment.create(null, savedMemberId, content, savedPostId);
        Long savedCommentId1 = postCommentRepository.save(postComment).getId();

        PostComment postComment2 = PostComment.create(null, savedMemberId, content, savedPostId);
        postComment2.increaseLikeCount();
        Long savedCommentId2 = postCommentRepository.save(postComment2).getId(); // 좋아요 더 많음

        //when & then
        FindAllPostCommentsParamRepositoryRequest request = new FindAllPostCommentsParamRepositoryRequest(0, 10, FindAllPostCommentsParamRepositoryRequest.OrderBy.RECENT_DATE);
        FindPostsAllCommentResponse response = new FindPostsAllCommentResponse(
            List.of(
                new FindPostsAllCommentResponse.CommentInfo(savedCommentId2, null, postComment2.getLikesCount(), content, nickname),
                new FindPostsAllCommentResponse.CommentInfo(savedCommentId1, null, postComment.getLikesCount(), content, nickname)
            )
        );
        assertThat(postQueryService.findAllCommentsByPostId(savedPostId, request)).isEqualTo(response);
    }

    @DisplayName("댓글을 좋아요 순으로 정렬해서 조회할 수 있다.")
    @Test
    void findAllCommentsByPostIdOlderByRecentDate() {
        //given
        String content = "내용";
        String nickname = "닉네임";
        String title = "제목";
        Member member = Member.create("asdf@example.com", nickname, Role.USER);
        Long savedMemberId = memberRepository.save(member).getId();

        Post post = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"));
        Long savedPostId = postRepository.save(post).getId();
        PostComment postComment = PostComment.create(null, savedMemberId, content, savedPostId);
        Long savedCommentId1 = postCommentRepository.save(postComment).getId();

        PostComment postComment2 = PostComment.create(null, savedMemberId, content, savedPostId);
        postComment2.increaseLikeCount();
        Long savedCommentId2 = postCommentRepository.save(postComment2).getId(); // 좋아요 더 많음

        //when & then
        FindAllPostCommentsParamRepositoryRequest request = new FindAllPostCommentsParamRepositoryRequest(0, 10, FindAllPostCommentsParamRepositoryRequest.OrderBy.LIKE_COUNT);
        FindPostsAllCommentResponse response = new FindPostsAllCommentResponse(
            List.of(
                new FindPostsAllCommentResponse.CommentInfo(savedCommentId2, null, postComment2.getLikesCount(), content, nickname),
                new FindPostsAllCommentResponse.CommentInfo(savedCommentId1, null, postComment.getLikesCount(), content, nickname)
            )
        );
        assertThat(postQueryService.findAllCommentsByPostId(savedPostId, request)).isEqualTo(response);
    }

    @DisplayName("댓글을 오래된 순으로 최근 순으로 정렬해서 조회할 수 있다.")
    @Test
    void findAllCommentsByPostIdOrderByLikeCount() {
        //given
        String content = "내용";
        String nickname = "닉네임";
        String title = "제목";
        Member member = Member.create("asdf@example.com", nickname, Role.USER);
        Long savedMemberId = memberRepository.save(member).getId();

        Post post = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"));
        Long savedPostId = postRepository.save(post).getId();
        PostComment postComment = PostComment.create(null, savedMemberId, content, savedPostId);
        Long savedCommentId1 = postCommentRepository.save(postComment).getId();

        PostComment postComment2 = PostComment.create(null, savedMemberId, content, savedPostId);
        postComment2.increaseLikeCount();
        Long savedCommentId2 = postCommentRepository.save(postComment2).getId();

        //when & then
        FindAllPostCommentsParamRepositoryRequest request = new FindAllPostCommentsParamRepositoryRequest(0, 10, FindAllPostCommentsParamRepositoryRequest.OrderBy.OLDER_DATE);
        FindPostsAllCommentResponse response = new FindPostsAllCommentResponse(
            List.of(
                new FindPostsAllCommentResponse.CommentInfo(savedCommentId1, null, postComment.getLikesCount(), content, nickname),
                new FindPostsAllCommentResponse.CommentInfo(savedCommentId2, null, postComment2.getLikesCount(), content, nickname)
            )
        );
        assertThat(postQueryService.findAllCommentsByPostId(savedPostId, request)).isEqualTo(response);
    }
}