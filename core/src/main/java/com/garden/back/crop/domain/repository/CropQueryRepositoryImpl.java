package com.garden.back.crop.domain.repository;

import com.garden.back.crop.domain.CropCategory;
import com.garden.back.crop.domain.TradeType;
import com.garden.back.crop.domain.repository.request.FindAllCropsPostRepositoryRequest;
import com.garden.back.crop.domain.repository.request.FindAllMyBookmarkCropPostsRepositoryRequest;
import com.garden.back.crop.domain.repository.request.FindAllMyBoughtCropPostsRepositoryRequest;
import com.garden.back.crop.domain.repository.request.FindAllMyCropPostsRepositoryRequest;
import com.garden.back.crop.domain.repository.response.*;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static com.garden.back.crop.domain.QCropBookmark.cropBookmark;
import static com.garden.back.crop.domain.QCropImage.cropImage;
import static com.garden.back.crop.domain.QCropPost.cropPost;
import static com.garden.back.member.QMember.member;
import static com.garden.back.member.region.QMemberAddress.memberAddress;

@Component
public class CropQueryRepositoryImpl implements CropQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CropQueryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public FindAllCropsPostResponse findAll(FindAllCropsPostRepositoryRequest request) {
        OrderSpecifier<?> orderBy = getAllCropsPostOrderBy(request.orderBy());

        List<FindAllCropsPostResponse.CropsInfo> cropsInfos = jpaQueryFactory
            .select(Projections.constructor(FindAllCropsPostResponse.CropsInfo.class,
                cropPost.id,
                cropPost.title,
                cropPost.price,
                cropPost.createdDate,
                cropPost.tradeType,
                cropPost.priceProposal,
                cropPost.tradeStatus,
                cropPost.cropCategory,
                cropPost.bookMarkCount,
                JPAExpressions
                    .select(cropImage.imageUrl)
                    .from(cropImage)
                    .where(cropImage.crop.id.eq(cropPost.id))
                    .limit(1L),
                memberAddress.address.fullAddress
            ))
            .from(cropPost)
            .leftJoin(member).on(cropPost.cropPostAuthorId.eq(member.id))
            .innerJoin(memberAddress).on(cropPost.memberAddressId.eq(memberAddress.id))
            .where(
                contentOrTitleLike(request.searchContent()),
                tradeType(request.tradeType()),
                cropCategoryType(request.cropCategory()),
                regionLike(request.region()),
                priceGreaterThanOrEqualTo(request.minPrice()),
                priceLessThanOrEqualTo(request.maxPrice())
            )
            .orderBy(orderBy)
            .offset(request.offset())
            .limit(request.limit())
            .fetch();

        return new FindAllCropsPostResponse(cropsInfos);
    }

    private BooleanExpression priceGreaterThanOrEqualTo(Integer minPrice) {
        return minPrice != null ? cropPost.price.goe(minPrice) : null;
    }

    private BooleanExpression priceLessThanOrEqualTo(Integer maxPrice) {
        return maxPrice != null ? cropPost.price.loe(maxPrice) : null;
    }


    private BooleanExpression regionLike(String region) {
        return !StringUtils.hasText(region) ? null : memberAddress.address.fullAddress.contains(region);
    }

    private BooleanExpression contentOrTitleLike(String searchContent) {
        return !StringUtils.hasText(searchContent) ? null : cropPost.title.contains(searchContent).or(cropPost.content.contains(searchContent));
    }

    private BooleanExpression tradeType(TradeType tradeType) {
        return Objects.nonNull(tradeType) ? cropPost.tradeType.eq(tradeType) : null;
    }

    private BooleanExpression cropCategoryType(CropCategory cropCategory) {
        return Objects.nonNull(cropCategory) ? cropPost.cropCategory.eq(cropCategory) : null;
    }


    private OrderSpecifier<?> getAllCropsPostOrderBy(FindAllCropsPostRepositoryRequest.OrderBy orderBy) {
        return switch (orderBy) {
            case LOWER_PRICE -> cropPost.price.asc();
            case HIGHER_PRICE -> cropPost.price.desc();
            case RECENT_DATE -> cropPost.createAt.desc();
            case BOOKMARK_COUNT -> cropPost.bookMarkCount.desc();
            case OLDER_DATE -> cropPost.createAt.asc();
        };
    }

    @Override
    public FindCropsPostDetailsResponse findCropPostDetails(Long id) {
        List<String> imageUrls = jpaQueryFactory.select(cropImage)
            .select(cropImage.imageUrl)
            .from(cropImage)
            .where(cropImage.crop.id.eq(id))
            .fetch();

        return jpaQueryFactory
            .select(Projections.constructor(FindCropsPostDetailsResponse.class,
                cropPost.content,
                member.nickname,
                member.memberMannerGrade,
                memberAddress.address.fullAddress,
                cropPost.cropCategory,
                cropPost.bookMarkCount,
                Expressions.constant(imageUrls)
            ))
            .from(cropPost)
            .leftJoin(member).on(cropPost.cropPostAuthorId.eq(member.id))
            .leftJoin(memberAddress).on(cropPost.memberAddressId.eq(memberAddress.id))
            .where(cropPost.id.eq(id))
            .fetchOne();
    }

    @Override
    public FindAllMyBookmarkCropPostsResponse findAllByMyBookmark(Long loginUserId, FindAllMyBookmarkCropPostsRepositoryRequest request) {
        List<FindAllMyBookmarkCropPostsResponse.CropInfo> cropInfos =
            jpaQueryFactory.select(Projections.constructor(FindAllMyBookmarkCropPostsResponse.CropInfo.class,
                    cropPost.id,
                    cropPost.title,
                    JPAExpressions
                        .select(cropImage.imageUrl)
                        .from(cropImage)
                        .where(cropImage.crop.id.eq(cropPost.id))
                        .limit(1L)
                ))
                .from(cropPost)
                .innerJoin(cropBookmark).on(cropPost.id.eq(cropBookmark.cropPostId))
                .where(cropBookmark.bookMarkOwnerId.eq(loginUserId))
                .offset(request.offset())
                .limit(request.limit())
                .fetch();

        return new FindAllMyBookmarkCropPostsResponse(cropInfos);
    }

    @Override
    public FindAllMyCropPostsResponse findAllMyCropPosts(Long loginUserId, FindAllMyCropPostsRepositoryRequest request) {
        List<FindAllMyCropPostsResponse.CropInfo> cropInfos =
            jpaQueryFactory.select(Projections.constructor(FindAllMyCropPostsResponse.CropInfo.class,
                    cropPost.id,
                    cropPost.title,
                    JPAExpressions
                        .select(cropImage.imageUrl)
                        .from(cropImage)
                        .where(cropImage.crop.id.eq(cropPost.id))
                        .limit(1L)
                ))
                .from(cropPost)
                .where(cropPost.cropPostAuthorId.eq(loginUserId))
                .offset(request.offset())
                .limit(request.limit())
                .fetch();

        return new FindAllMyCropPostsResponse(cropInfos);
    }

    @Override
    public FindAllMyBoughtCropPostsResponse findAllMyBoughtCrops(FindAllMyBoughtCropPostsRepositoryRequest request) {
        List<FindAllMyBoughtCropPostsResponse.CropInfo> cropInfos =
            jpaQueryFactory.select(Projections.constructor(FindAllMyBoughtCropPostsResponse.CropInfo.class,
                    cropPost.id,
                    cropPost.title,
                    JPAExpressions
                        .select(cropImage.imageUrl)
                        .from(cropImage)
                        .where(cropImage.crop.id.eq(cropPost.id))
                        .limit(1L)
                ))
                .from(cropPost)
                .where(cropPost.buyerId.eq(request.loginUserId()))
                .offset(request.offset())
                .limit(request.limit())
                .fetch();

        return new FindAllMyBoughtCropPostsResponse(cropInfos);
    }
}