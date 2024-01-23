package com.garden.back.post.domain.repository;

import com.garden.back.post.domain.repository.request.*;
import com.garden.back.post.domain.repository.response.*;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.garden.back.member.QMember.member;
import static com.garden.back.post.domain.QPost.post;
import static com.garden.back.post.domain.QPostComment.postComment;
import static com.garden.back.post.domain.QPostImage.postImage;
import static com.garden.back.post.domain.QPostLike.postLike;

@Repository
public class PostQueryRepositoryImpl implements PostQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public PostQueryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public FindPostDetailsResponse findPostDetails(Long id) {
        List<String> imageUrls = jpaQueryFactory
            .select(postImage.imageUrl)
            .from(postImage)
            .where(postImage.post.id.eq(id))
            .fetch();

        return jpaQueryFactory
            .select(Projections.constructor(FindPostDetailsResponse.class,
                post.commentsCount,
                post.likesCount,
                member.nickname,
                post.content,
                post.title,
                post.createdDate,
                Expressions.constant(imageUrls)))
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
            .where(contentOrTitleLike(request.searchContent()))
            .orderBy(orderBy)
            .offset(request.offset())
            .limit(request.limit())
            .fetch();

        return new FindAllPostsResponse(posts);
    }

    private BooleanExpression contentOrTitleLike(String searchContent) {
        return !StringUtils.hasText(searchContent) ? null : post.title.contains(searchContent).or(post.content.contains(searchContent));
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


    @Override
    public FindAllMyLikePostsResponse findAllByMyLike(Long loginUserId, FindAllMyLikePostsRepositoryRequest request) {
        List<FindAllMyLikePostsResponse.PostInfo> postInfos =  jpaQueryFactory
            .select(Projections.constructor(FindAllMyLikePostsResponse.PostInfo.class,
                post.id,
                post.title
            ))
            .from(post)
            .join(postLike).on(post.id.eq(postLike.postId))
            .where(postLike.likesClickerId.eq(loginUserId))
            .offset(request.offset())
            .limit(request.limit())
            .fetch();

        return new FindAllMyLikePostsResponse(postInfos);
    }

    @Override
    public FindAllMyPostsResponse findAllMyPosts(Long loginUserId, FindAllMyPostsRepositoryRequest request) {
        List<FindAllMyPostsResponse.PostInfo> postInfos =  jpaQueryFactory
            .select(Projections.constructor(FindAllMyPostsResponse.PostInfo.class,
                post.id,
                post.title
            ))
            .from(post)
            .where(post.postAuthorId.eq(loginUserId))
            .offset(request.offset())
            .limit(request.limit())
            .fetch();

        return new FindAllMyPostsResponse(postInfos);
    }

    @Override
    public FindAllMyCommentPostsResponse findAllByMyComment(Long loginUserId, FindAllMyCommentPostsRepositoryRequest request) {
        List<FindAllMyCommentPostsResponse.PostInfo> postInfos =  jpaQueryFactory
            .select(Projections.constructor(FindAllMyCommentPostsResponse.PostInfo.class,
                post.id,
                post.title
            ))
            .from(post)
            .join(postComment).on(post.id.eq(postComment.postId))
            .where(postComment.authorId.eq(loginUserId))
            .offset(request.offset())
            .limit(request.limit())
            .fetch();

        return new FindAllMyCommentPostsResponse(postInfos);
    }
}
