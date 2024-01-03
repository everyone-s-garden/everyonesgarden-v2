package com.garden.back.post.domain.repository;

import com.garden.back.post.domain.repository.request.FindAllPostCommentsParamRepositoryRequest;
import com.garden.back.post.domain.repository.request.FindAllPostParamRepositoryRequest;
import com.garden.back.post.domain.repository.response.FindAllPostsResponse;
import com.garden.back.post.domain.repository.response.FindPostDetailsResponse;
import com.garden.back.post.domain.repository.response.FindPostsAllCommentResponse;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.garden.back.member.QMember.member;
import static com.garden.back.post.domain.QPost.post;
import static com.garden.back.post.domain.QPostComment.postComment;

@Repository
public class PostQueryRepositoryImpl implements PostQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public PostQueryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public FindPostDetailsResponse findPostDetails(Long id) {
        return jpaQueryFactory
            .select(Projections.constructor(FindPostDetailsResponse.class,
                post.commentsCount,
                post.likesCount,
                member.nickname,
                post.content,
                post.title,
                post.createdDate))
            .from(post)
            .leftJoin(member).on(post.postAuthorId.eq(member.id))
            .where(post.id.eq(id))
            .fetchOne();
    }

    @Override
    public FindAllPostsResponse findAllPosts(FindAllPostParamRepositoryRequest request) {
        OrderSpecifier<?> orderBy = getPostsOrderBy(request.orderBy());
        List<FindAllPostsResponse.PostInfo> posts = jpaQueryFactory
            .select(Projections.constructor(FindAllPostsResponse.PostInfo.class,
                post.id,
                post.title,
                post.likesCount,
                post.commentsCount,
                post.createdDate))
            .from(post)
            .orderBy(orderBy)
            .offset(request.offset())
            .limit(request.limit())
            .fetch();

        return new FindAllPostsResponse(posts);
    }

    private OrderSpecifier<?> getPostsOrderBy(FindAllPostParamRepositoryRequest.OrderBy orderBy) {
        return switch (orderBy) {
            case COMMENT_COUNT -> post.commentsCount.desc();
            case RECENT_DATE -> post.createAt.desc();
            case LIKE_COUNT -> post.likesCount.desc();
            case OLDER_DATE -> post.createAt.asc();
        };
    }

    @Override
    public FindPostsAllCommentResponse findPostsAllComments(Long id, FindAllPostCommentsParamRepositoryRequest request) {
        OrderSpecifier<?> orderBy = getCommentsOrderBy(request.orderBy());
        List<FindPostsAllCommentResponse.CommentInfo> comments = jpaQueryFactory
            .select(Projections.constructor(FindPostsAllCommentResponse.CommentInfo.class,
                postComment.id,
                postComment.parentCommentId,
                postComment.likesCount,
                postComment.content,
                member.nickname
            ))
            .from(postComment)
            .where(postComment.postId.eq(id))
            .leftJoin(member).on(postComment.authorId.eq(member.id))
            .orderBy(orderBy)
            .offset(request.offset())
            .limit(request.limit())
            .fetch();

        return new FindPostsAllCommentResponse(comments);
    }

    private OrderSpecifier<?> getCommentsOrderBy(FindAllPostCommentsParamRepositoryRequest.OrderBy orderBy) {
        return switch (orderBy) {
            case RECENT_DATE -> postComment.createAt.desc();
            case LIKE_COUNT -> postComment.likesCount.desc();
            case OLDER_DATE -> postComment.createAt.asc();
        };
    }
}
