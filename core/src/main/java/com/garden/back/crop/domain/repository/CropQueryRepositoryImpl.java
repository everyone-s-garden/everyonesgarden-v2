package com.garden.back.crop.domain.repository;

import com.garden.back.crop.FindAllCropsPostResponse;
import com.garden.back.crop.FindCropsPostDetailsResponse;
import com.garden.back.crop.domain.CropCategory;
import com.garden.back.crop.domain.TradeType;
import com.garden.back.crop.domain.repository.request.FindAllCropsPostRepositoryRequest;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static com.garden.back.crop.domain.QCropImage.cropImage;
import static com.garden.back.crop.domain.QCropPost.cropPost;
import static com.garden.back.member.QMember.member;

@Component
public class CropQueryRepositoryImpl implements CropQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CropQueryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
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
                cropPost.bookMarkCount
            ))
            .from(cropPost)
            .leftJoin(member).on(cropPost.cropPostAuthorId.eq(member.id))
            .where(contentOrTitleLike(request.searchContent()), tradeType(request.tradeType()), cropCategoryType(request.cropCategory()))
            .orderBy(orderBy)
            .offset(request.offset())
            .limit(request.limit())
            .fetch();

        return new FindAllCropsPostResponse(cropsInfos);
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
                member.mannerScore,
                member.address,
                cropPost.cropCategory,
                cropPost.bookMarkCount,
                Expressions.constant(imageUrls)
            ))
            .from(cropPost)
            .leftJoin(member).on(cropPost.cropPostAuthorId.eq(member.id))
            .where(cropPost.id.eq(id))
            .fetchOne();
    }
}
