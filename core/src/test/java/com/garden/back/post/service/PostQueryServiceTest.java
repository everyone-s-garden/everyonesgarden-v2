package com.garden.back.post.service;

import com.garden.back.global.IntegrationTestSupport;
import com.garden.back.member.Member;
import com.garden.back.member.Role;
import com.garden.back.member.repository.MemberRepository;
import com.garden.back.post.domain.Post;
import com.garden.back.post.domain.PostComment;
import com.garden.back.post.domain.PostLike;
import com.garden.back.post.domain.PostType;
import com.garden.back.post.domain.repository.PostCommentRepository;
import com.garden.back.post.domain.repository.PostLikeRepository;
import com.garden.back.post.domain.repository.PostRepository;
import com.garden.back.post.domain.repository.request.*;
import com.garden.back.post.domain.repository.response.*;
import com.garden.back.post.service.request.CommentCreateServiceRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
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

    @Autowired
    PostCommandService postCommandService;

    @Autowired
    PostLikeRepository postLikeRepository;

    @DisplayName("게시글 id로 게시글의 상세를 조회할 수 있다.(댓글 수, 좋아요 수, 작성자, 제목, 내용)")
    @Test
    void findPostById() {
        //given
        String content = "내용";
        String nickname = "닉네임";
        String title = "제목";
        String imageUrl = "http://example.com/image.jpg";
        Member member = Member.create("asdf@example.com", nickname, Role.USER);
        Long savedMemberId = memberRepository.save(member).getId();
        Post post = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"), PostType.QUESTION);
        Post savedPost = postRepository.save(post);
        postCommandService.addLikeToPost(post.getId(), savedMemberId);
        FindPostDetailsResponse response = new FindPostDetailsResponse(
            post.getCommentsCount(),
            post.getLikesCount(),
            new UserResponse(
                member.getId(),
                member.getProfileImageUrl(),
                member.getNickname(),
                member.getMemberMannerGrade()
            ),
            content,
            title,
            savedPost.getCreatedDate(),
            true,
            post.getPostType(),
            List.of(imageUrl)
        );

        //when & then
        assertThat(postQueryService.findPostById(savedPost.getId(), savedMemberId)).isEqualTo(response);
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

        Post post = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"), PostType.QUESTION);
        post.increaseLikeCount(); // 좋아요 수 많음, 더 최근 게시글
        Long savedPostId1 = postRepository.save(post).getId();

        Post post2 = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"), PostType.QUESTION);
        post2.increaseCommentCount(); //댓글 수 많음, 더 늦은 게시글
        Long savedPostId2 = postRepository.save(post2).getId();

        List<FindAllPostsResponse.PostInfo> postInfosForCommentCount = List.of(
            new FindAllPostsResponse.PostInfo(
                savedPostId2,
                title,
                post2.getLikesCount(),
                post2.getCommentsCount(),
                post2.getContent(),
                post2.getPostImages().stream().findFirst().get().getImageUrl(),
                new UserResponse(
                    member.getId(),
                    member.getProfileImageUrl(),
                    member.getNickname(),
                    member.getMemberMannerGrade()
                ),
                post2.getPostType(),
                post2.getCreatedDate(),
                false
            ),
            new FindAllPostsResponse.PostInfo(
                savedPostId1,
                title,
                post.getLikesCount(),
                post.getCommentsCount(),
                post.getContent(),
                post.getPostImages().stream().findFirst().get().getImageUrl(),
                new UserResponse(
                    member.getId(),
                    member.getProfileImageUrl(),
                    member.getNickname(),
                    member.getMemberMannerGrade()
                ),
                post.getPostType(),
                post.getCreatedDate(),
                false
            ) //Post2가 댓글 더 많음
        );

        FindAllPostParamRepositoryRequest request = new FindAllPostParamRepositoryRequest(0, 10, title, PostType.QUESTION, FindAllPostParamRepositoryRequest.OrderBy.COMMENT_COUNT, -1L);

        //when & then
        FindAllPostsResponse expectedResponseForCommentCount = new FindAllPostsResponse(postInfosForCommentCount);
        assertThat(postQueryService.findAllPosts(request)).isEqualTo(expectedResponseForCommentCount);
    }

    @DisplayName("모든 게시글을 게시글 타입으로 조회할 수 있다.")
    @Test
    void findAllPostsByPostType() {
        //given
        String content = "내용";
        String nickname = "닉네임";
        String title = "제목";
        Member member = Member.create("asdf@example.com", nickname, Role.USER);
        Long savedMemberId = memberRepository.save(member).getId();

        Post post = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"), PostType.QUESTION);
        post.increaseLikeCount(); // 좋아요 수 많음, 더 최근 게시글
        Long savedPostId1 = postRepository.save(post).getId();

        Post post2 = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"), PostType.ETC);
        post2.increaseCommentCount(); //댓글 수 많음, 더 늦은 게시글
        Long savedPostId2 = postRepository.save(post2).getId();

        List<FindAllPostsResponse.PostInfo> postInfosForCommentCount = List.of(
            new FindAllPostsResponse.PostInfo(
                savedPostId1,
                title,
                post.getLikesCount(),
                post.getCommentsCount(),
                post.getContent(),
                post.getPostImages().stream().findFirst().get().getImageUrl(),
                new UserResponse(
                    member.getId(),
                    member.getProfileImageUrl(),
                    member.getNickname(),
                    member.getMemberMannerGrade()
                ),
                post.getPostType(),
                post.getCreatedDate(),
                false) //Post2가 댓글 더 많음
        );

        FindAllPostParamRepositoryRequest request = new FindAllPostParamRepositoryRequest(0, 10, title, PostType.QUESTION, FindAllPostParamRepositoryRequest.OrderBy.COMMENT_COUNT, -1L);

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

        Post post = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"), PostType.QUESTION);
        post.increaseLikeCount(); // 좋아요 수 많음, 더 최근 게시글
        Long savedPostId1 = postRepository.save(post).getId();

        Post post2 = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"), PostType.QUESTION);
        post2.increaseCommentCount(); //댓글 수 많음, 더 늦은 게시글
        Long savedPostId2 = postRepository.save(post2).getId();

        List<FindAllPostsResponse.PostInfo> postInfosForCommentCount = List.of(
            new FindAllPostsResponse.PostInfo(
                savedPostId1,
                title,
                post.getLikesCount(),
                post.getCommentsCount(),
                post.getContent(),
                post.getPostImages().stream().findFirst().get().getImageUrl(),
                new UserResponse(
                    member.getId(),
                    member.getProfileImageUrl(),
                    member.getNickname(),
                    member.getMemberMannerGrade()
                ),
                post.getPostType(),
                post.getCreatedDate(),
                false),
            new FindAllPostsResponse.PostInfo(
                savedPostId2,
                title,
                post2.getLikesCount(),
                post2.getCommentsCount(),
                post2.getContent(),
                post2.getPostImages().stream().findFirst().get().getImageUrl(),
                new UserResponse(
                    member.getId(),
                    member.getProfileImageUrl(),
                    member.getNickname(),
                    member.getMemberMannerGrade()
                ),
                post2.getPostType(),
                post2.getCreatedDate(),
                false
            )
        );

        FindAllPostParamRepositoryRequest request = new FindAllPostParamRepositoryRequest(0, 10, title, PostType.QUESTION, FindAllPostParamRepositoryRequest.OrderBy.LIKE_COUNT, -1L);

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

        Post post = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"), PostType.QUESTION);
        post.increaseLikeCount(); // 좋아요 수 많음, 더 최근 게시글
        Long savedPostId1 = postRepository.save(post).getId();

        Post post2 = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"), PostType.QUESTION);
        post2.increaseCommentCount(); //댓글 수 많음, 더 늦은 게시글
        Long savedPostId2 = postRepository.save(post2).getId();

        List<FindAllPostsResponse.PostInfo> postInfosForCommentCount = List.of(
            new FindAllPostsResponse.PostInfo(
                savedPostId2,
                title,
                post2.getLikesCount(),
                post2.getCommentsCount(),
                post2.getContent(),
                post2.getPostImages().stream().findFirst().get().getImageUrl(),
                new UserResponse(
                    member.getId(),
                    member.getProfileImageUrl(),
                    member.getNickname(),
                    member.getMemberMannerGrade()
                ),
                post2.getPostType(),
                post2.getCreatedDate(),
                false),
            new FindAllPostsResponse.PostInfo(
                savedPostId1,
                title,
                post.getLikesCount(),
                post.getCommentsCount(),
                post.getContent(),
                post.getPostImages().stream().findFirst().get().getImageUrl(),
                new UserResponse(
                    member.getId(),
                    member.getProfileImageUrl(),
                    member.getNickname(),
                    member.getMemberMannerGrade()
                ),
                post.getPostType(),
                post.getCreatedDate(),
                false
            ) //Post2가 댓글 더 많음
        );

        FindAllPostParamRepositoryRequest request = new FindAllPostParamRepositoryRequest(0, 10, null, PostType.QUESTION, FindAllPostParamRepositoryRequest.OrderBy.RECENT_DATE, -1L);

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

        Post post = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"), PostType.QUESTION);
        post.increaseLikeCount(); // 좋아요 수 많음, 더 최근 게시글
        Long savedPostId1 = postRepository.save(post).getId();

        Post post2 = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"), PostType.QUESTION);
        post2.increaseCommentCount(); //댓글 수 많음, 더 늦은 게시글
        Long savedPostId2 = postRepository.save(post2).getId();

        List<FindAllPostsResponse.PostInfo> postInfosForCommentCount = List.of(
            new FindAllPostsResponse.PostInfo(
                savedPostId1,
                title,
                post.getLikesCount(),
                post.getCommentsCount(),
                post.getContent(),
                post.getPostImages().stream().findFirst().get().getImageUrl(),
                new UserResponse(
                    member.getId(),
                    member.getProfileImageUrl(),
                    member.getNickname(),
                    member.getMemberMannerGrade()
                ),
                post.getPostType(),
                post.getCreatedDate(),
                false),
            new FindAllPostsResponse.PostInfo(
                savedPostId2,
                title,
                post2.getLikesCount(),
                post2.getCommentsCount(),
                post2.getContent(),
                post2.getPostImages().stream().findFirst().get().getImageUrl(),
                new UserResponse(
                    member.getId(),
                    member.getProfileImageUrl(),
                    member.getNickname(),
                    member.getMemberMannerGrade()
                ),
                post2.getPostType(),
                post2.getCreatedDate(),
                false)
        );

        FindAllPostParamRepositoryRequest request = new FindAllPostParamRepositoryRequest(0, 10, title, PostType.QUESTION, FindAllPostParamRepositoryRequest.OrderBy.OLDER_DATE, -1L);

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

        Post post = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"), PostType.QUESTION);
        Long savedPostId = postRepository.save(post).getId();
        PostComment postComment = PostComment.create(null, savedMemberId, content, savedPostId);
        Long olderCommentId = postCommentRepository.save(postComment).getId();

        PostComment postComment2 = PostComment.create(null, savedMemberId, content, savedPostId);

        Long recentCommentId = postCommentRepository.save(postComment2).getId();
        postCommandService.addLikeToComment(recentCommentId, savedMemberId);

        //when & then
        FindAllPostCommentsParamRepositoryRequest request = new FindAllPostCommentsParamRepositoryRequest(0, 10, FindAllPostCommentsParamRepositoryRequest.OrderBy.RECENT_DATE);
        FindPostsAllCommentResponse response = new FindPostsAllCommentResponse(
            List.of(
                new FindPostsAllCommentResponse.ParentInfo(
                    recentCommentId,
                    postComment2.getLikesCount(),
                    content,
                    new UserResponse(
                        member.getId(),
                        member.getProfileImageUrl(),
                        member.getNickname(),
                        member.getMemberMannerGrade()
                    ),
                    true,
                    Collections.EMPTY_LIST,
                    postComment2.getCreateAt()
                ),
                new FindPostsAllCommentResponse.ParentInfo(
                    olderCommentId,
                    postComment.getLikesCount(),
                    content,
                    new UserResponse(
                        member.getId(),
                        member.getProfileImageUrl(),
                        member.getNickname(),
                        member.getMemberMannerGrade()
                    ),
                    false,
                    Collections.EMPTY_LIST,
                    postComment.getCreateAt()
                )
            )
        );
        assertThat(postQueryService.findAllCommentsByPostId(savedPostId, savedMemberId, request)).isEqualTo(response);
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

        Post post = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"), PostType.QUESTION);
        Long savedPostId = postRepository.save(post).getId();
        PostComment postComment = PostComment.create(null, savedMemberId, content, savedPostId);
        Long haveLessCommentLikeId = postCommentRepository.save(postComment).getId();

        PostComment postComment2 = PostComment.create(null, savedMemberId, content, savedPostId);
        Long havMoreLikeCommentId = postCommentRepository.save(postComment2).getId(); // 좋아요 더 많음
        postCommandService.addLikeToComment(havMoreLikeCommentId, savedMemberId);

        //when & then
        FindAllPostCommentsParamRepositoryRequest request = new FindAllPostCommentsParamRepositoryRequest(0, 10, FindAllPostCommentsParamRepositoryRequest.OrderBy.LIKE_COUNT);

        FindPostsAllCommentResponse response = new FindPostsAllCommentResponse(
            List.of(
                new FindPostsAllCommentResponse.ParentInfo(
                    havMoreLikeCommentId,
                    postComment2.getLikesCount(),
                    content,
                    new UserResponse(
                        member.getId(),
                        member.getProfileImageUrl(),
                        member.getNickname(),
                        member.getMemberMannerGrade()
                    ),
                    true,
                    Collections.EMPTY_LIST,
                    postComment2.getCreateAt()),
                new FindPostsAllCommentResponse.ParentInfo(
                    haveLessCommentLikeId,
                    postComment.getLikesCount(),
                    content,
                    new UserResponse(
                        member.getId(),
                        member.getProfileImageUrl(),
                        member.getNickname(),
                        member.getMemberMannerGrade()
                    ),
                    false,
                    Collections.EMPTY_LIST,
                    postComment.getCreateAt()
                )
            )
        );

        assertThat(postQueryService.findAllCommentsByPostId(savedPostId, savedMemberId, request)).isEqualTo(response);
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

        Post post = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"), PostType.QUESTION);
        Long savedPostId = postRepository.save(post).getId();
        PostComment postComment = PostComment.create(null, savedMemberId, content, savedPostId);
        Long olderCommentId = postCommentRepository.save(postComment).getId();

        PostComment postComment2 = PostComment.create(null, savedMemberId, content, savedPostId);
        Long recentCommentId = postCommentRepository.save(postComment2).getId();
        postCommandService.addLikeToComment(recentCommentId, savedMemberId);

        //when & then
        FindAllPostCommentsParamRepositoryRequest request = new FindAllPostCommentsParamRepositoryRequest(0, 10, FindAllPostCommentsParamRepositoryRequest.OrderBy.OLDER_DATE);
        FindPostsAllCommentResponse response = new FindPostsAllCommentResponse(
            List.of(
                new FindPostsAllCommentResponse.ParentInfo(
                    olderCommentId,
                    postComment.getLikesCount(),
                    content,
                    new UserResponse(
                        member.getId(),
                        member.getProfileImageUrl(),
                        member.getNickname(),
                        member.getMemberMannerGrade()
                    ),
                    false,
                    Collections.EMPTY_LIST,
                    postComment.getCreateAt()
                ),
                new FindPostsAllCommentResponse.ParentInfo(
                    recentCommentId,
                    postComment2.getLikesCount(),
                    content,
                    new UserResponse(
                        member.getId(),
                        member.getProfileImageUrl(),
                        member.getNickname(),
                        member.getMemberMannerGrade()
                    ),
                    true,
                    Collections.EMPTY_LIST,
                    postComment2.getCreateAt()
                )
            )
        );
        assertThat(postQueryService.findAllCommentsByPostId(savedPostId, savedMemberId, request)).isEqualTo(response);
    }


    @DisplayName("내가 좋아요한 게시글을 조회한다.")
    @Test
    void findAllByMyLike() {
        //given
        String content = "내용";
        String nickname = "닉네임";
        String title = "제목";
        Member member = Member.create("asdf@example.com", nickname, Role.USER);
        Long savedMemberId = memberRepository.save(member).getId();
        Post post = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"), PostType.QUESTION);
        postRepository.save(post);
        postCommandService.addLikeToPost(post.getId(), member.getId());
        FindAllMyLikePostsResponse expected =
            new FindAllMyLikePostsResponse(
                List.of(
                    new FindAllMyLikePostsResponse.PostInfo(
                        post.getId(),
                        post.getTitle(),
                        post.getPostImages().stream().findFirst().get().getImageUrl(),
                        post.getContent(),
                        post.getLikesCount(),
                        post.getCommentsCount(),
                        new UserResponse(
                            member.getId(),
                            member.getProfileImageUrl(),
                            member.getNickname(),
                            member.getMemberMannerGrade()
                    )
                )
            ));

        //when & then
        FindAllMyLikePostsResponse actual = postQueryService.findAllByMyLike(member.getId(), new FindAllMyLikePostsRepositoryRequest(0L, 10L));
        assertThat(expected).isEqualTo(actual);

    }

    @DisplayName("내가 작성한 게시글을 조회한다.")
    @Test
    void findAllMyPosts() {
        //given
        String content = "내용";
        String nickname = "닉네임";
        String title = "제목";
        Member member = Member.create("asdf@example.com", nickname, Role.USER);
        Long savedMemberId = memberRepository.save(member).getId();
        Post post = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"), PostType.QUESTION);
        postRepository.save(post);
        FindAllMyPostsResponse expected = new FindAllMyPostsResponse(
            List.of(
                new FindAllMyPostsResponse.PostInfo(
                    post.getId(),
                    post.getTitle(),
                    post.getPostImages().stream().findFirst().get().getImageUrl(),
                    post.getContent(),
                    post.getLikesCount(),
                    post.getCommentsCount(),
                    new UserResponse(
                        member.getId(),
                        member.getProfileImageUrl(),
                        member.getNickname(),
                        member.getMemberMannerGrade()
                )
            )
        ));

        //when & then
        FindAllMyPostsResponse actual = postQueryService.findAllMyPosts(member.getId(), new FindAllMyPostsRepositoryRequest(0L, 10L));
        assertThat(expected).isEqualTo(actual);


    }

    @DisplayName("내가 작성한 댓글의 게시글을 조회한다.")
    @Test
    void findAllByMyComment() {
        //given
        String content = "내용";
        String nickname = "닉네임";
        String title = "제목";
        Member member = Member.create("asdf@example.com", nickname, Role.USER);
        Long savedMemberId = memberRepository.save(member).getId();
        Post post = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"), PostType.QUESTION);
        postRepository.save(post);
        String commentContent = "내용";
        postCommandService.createComment(post.getId(), member.getId(), new CommentCreateServiceRequest(commentContent, null));
        FindAllMyCommentPostsResponse expected = new FindAllMyCommentPostsResponse(
            List.of(
                new FindAllMyCommentPostsResponse.PostInfo(
                    post.getId(),
                    post.getTitle(),
                    post.getPostImages().stream().findFirst().get().getImageUrl(),
                    commentContent,
                    post.getLikesCount(),
                    post.getCommentsCount(),
                    new UserResponse(
                        member.getId(),
                        member.getProfileImageUrl(),
                        member.getNickname(),
                        member.getMemberMannerGrade()
                )
            )
        ));

        //when & then
        FindAllMyCommentPostsResponse actual = postQueryService.findAllByMyComment(member.getId(), new FindAllMyCommentPostsRepositoryRequest(0L, 10L));
        assertThat(expected).isEqualTo(actual);
    }

    @DisplayName("실시간 인기 게시글을 조회한다.")
    @Test
    void findPopularPosts() {
        //given
        String content = "내용";
        String nickname = "닉네임";
        String title = "제목";
        Member member = Member.create("asdf@example.com", nickname, Role.USER);
        Long savedMemberId = memberRepository.save(member).getId();

        Post post = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"), PostType.QUESTION);
        post.increaseLikeCount(); // 좋아요 수 많음
        Long savedPostId1 = postRepository.save(post).getId();

        Post post2 = Post.create(title, content, savedMemberId, List.of("http://example.com/image.jpg"), PostType.QUESTION);
        post2.increaseCommentCount(); //댓글 수 많음
        Long savedPostId2 = postRepository.save(post2).getId();
        postLikeRepository.save(PostLike.create(savedMemberId, post.getId()));

        //when & then
        FindAllPopularRepositoryPostsRequest request = new FindAllPopularRepositoryPostsRequest(0L, 10L, 1, savedMemberId);
        FindAllPopularPostsResponse response = new FindAllPopularPostsResponse(List.of(
            new FindAllPopularPostsResponse.PostInfo(
                savedPostId1,
                title,
                post.getLikesCount(),
                post.getCommentsCount(),
                post.getContent(),
                post.getPostImages().stream().findFirst().get().getImageUrl(),
                new UserResponse(
                    member.getId(),
                    member.getProfileImageUrl(),
                    member.getNickname(),
                    member.getMemberMannerGrade()
                ),
                post.getPostType(),
                post.getCreatedDate(),
                true),
            new FindAllPopularPostsResponse.PostInfo(
                savedPostId2,
                title,
                post2.getLikesCount(),
                post2.getCommentsCount(),
                post2.getContent(),
                post2.getPostImages().stream().findFirst().get().getImageUrl(),
                new UserResponse(
                    member.getId(),
                    member.getProfileImageUrl(),
                    member.getNickname(),
                    member.getMemberMannerGrade()
                ),
                post.getPostType(),
                post.getCreatedDate(),
                false)
        ));
        assertThat(postQueryService.findAllPopularPosts(request)).isEqualTo(response);
    }
}
