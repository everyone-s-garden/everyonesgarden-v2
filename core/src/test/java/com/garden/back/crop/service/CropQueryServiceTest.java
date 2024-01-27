package com.garden.back.crop.service;

import com.garden.back.crop.domain.*;
import com.garden.back.crop.domain.repository.CropBookmarkJpaRepository;
import com.garden.back.crop.domain.repository.CropJpaRepository;
import com.garden.back.crop.domain.repository.request.FindAllCropsPostRepositoryRequest;
import com.garden.back.crop.domain.repository.request.FindAllMyBookmarkCropPostsRepositoryRequest;
import com.garden.back.crop.domain.repository.request.FindAllMyBoughtCropPostsRepositoryRequest;
import com.garden.back.crop.domain.repository.request.FindAllMyCropPostsRepositoryRequest;
import com.garden.back.crop.domain.repository.response.*;
import com.garden.back.global.IntegrationTestSupport;
import com.garden.back.member.Member;
import com.garden.back.member.Role;
import com.garden.back.member.region.MemberAddress;
import com.garden.back.member.repository.MemberAddressRepository;
import com.garden.back.member.repository.MemberRepository;
import com.garden.back.region.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class CropQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    CropQueryService cropQueryService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CropJpaRepository cropJpaRepository;

    @Autowired
    MemberAddressRepository memberAddressRepository;

    @Autowired
    CropBookmarkJpaRepository cropBookmarkJpaRepository;

    Long cropPostId;
    CropPost cropPost;
    String expectedUrl;
    Long loginUserId;
    CropPost deliveryTradePost;

    @BeforeEach
    void setUp() {
        expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        loginUserId = 1L;
        cropPost = CropPost.create("내용", "제목", CropCategory.FRUIT, 100000, false, TradeType.DIRECT_TRADE, List.of(expectedUrl), loginUserId, 1L);
        cropPostId = cropJpaRepository.save(cropPost).getId();
        deliveryTradePost = CropPost.create("내용2", "제목2", CropCategory.VEGETABLE, 200000, true, TradeType.DELIVERY_TRADE, List.of(expectedUrl), loginUserId, 1L);
        cropJpaRepository.save(deliveryTradePost);
    }


    @DisplayName("모든 작물 게시글을 동적 검색으로 조회한다.")
    @ParameterizedTest
    @MethodSource("provideRequests")
    void findAll(FindAllCropsPostRepositoryRequest request) {
        FindAllCropsPostResponse response = cropQueryService.findAll(request);
        assertThat(response.cropsInfos()).allMatch(info -> info.tradeType().equals(request.tradeType()))
            .allMatch(info -> info.cropCategory().equals(request.cropCategory()))
            .allMatch(info -> info.title().contains(request.searchContent()))
            .allMatch(info -> info.region().contains(request.region()))
            .allMatch(info -> info.price() >= request.minPrice() &&
                info.price() <= request.maxPrice());


        switch (request.orderBy()) {
            case RECENT_DATE:
                assertThat(response.cropsInfos())
                    .isSortedAccordingTo(Comparator.comparing(FindAllCropsPostResponse.CropsInfo::createdDate).reversed());
                break;
            case BOOKMARK_COUNT:
                assertThat(response.cropsInfos())
                    .isSortedAccordingTo(Comparator.comparing(FindAllCropsPostResponse.CropsInfo::bookmarkCount).reversed());
                break;
            case OLDER_DATE:
                assertThat(response.cropsInfos())
                    .isSortedAccordingTo(Comparator.comparing(FindAllCropsPostResponse.CropsInfo::createdDate));
                break;
            case LOWER_PRICE:
                assertThat(response.cropsInfos())
                    .isSortedAccordingTo(Comparator.comparing(FindAllCropsPostResponse.CropsInfo::price));
                break;
            case HIGHER_PRICE:
                assertThat(response.cropsInfos())
                    .isSortedAccordingTo(Comparator.comparing(FindAllCropsPostResponse.CropsInfo::price).reversed());
                break;
        }
    }

    private static Stream<FindAllCropsPostRepositoryRequest> provideRequests() {
        return Stream.of(
                TradeType.values())
            .flatMap(cropCategory -> Stream.of(CropCategory.values())
                .flatMap(tradeType -> Stream.of(FindAllCropsPostRepositoryRequest.OrderBy.values())
                    .map(orderBy -> new FindAllCropsPostRepositoryRequest(
                        0, 10, "제목", cropCategory, tradeType, "서울시", 0, 10000, orderBy))
                )
            );
    }

    @DisplayName("작물 게시글의 세부 정보를 조회한다.")
    @Test
    void findCropsPostDetails() {
        String email = "abc@naver.com";
        String nickname = "asdf";
        Member member = Member.create(email, nickname, Role.USER);

        memberRepository.save(member);
        Member savedMember = memberRepository.save(member);
        MemberAddress memberAddress = MemberAddress.create(new Address("서울시", "강남구", "역삼동"), savedMember.getId());
        memberAddressRepository.save(memberAddress);

        FindCropsPostDetailsResponse response = sut.giveMeBuilder(FindCropsPostDetailsResponse.class)
            .set("content", cropPost.getContent())
            .set("author", nickname)
            .set("memberMannerGrade", member.getMemberMannerGrade())
            .set("address", memberAddress.getAddress().getFullAddress())
            .set("cropCategory", cropPost.getCropCategory())
            .set("bookmarkCount", cropPost.getBookMarkCount())
            .size("images", 1)
            .set("images[0]", expectedUrl)
            .sample();
        assertThat(cropQueryService.findCropsPostDetails(cropPostId)).isEqualTo(response);
    }

    @DisplayName("내가 작성한 작물 게시글을 조회할 수 있다.")
    @Test
    void findAllMyCropPosts() {
        //given
        FindAllMyCropPostsRepositoryRequest request = new FindAllMyCropPostsRepositoryRequest(0L, 10L);

        //when & then
        FindAllMyCropPostsResponse expected = new FindAllMyCropPostsResponse(
            List.of(
                new FindAllMyCropPostsResponse.CropInfo(cropPostId, cropPost.getTitle(), cropPost.getCropImages().stream().findFirst().get().getImageUrl()),
                new FindAllMyCropPostsResponse.CropInfo(deliveryTradePost.getId(), deliveryTradePost.getTitle(), deliveryTradePost.getCropImages().stream().findFirst().get().getImageUrl())
            )
        );
        assertThat(cropQueryService.findAllMyCropPosts(loginUserId, request)).isEqualTo(expected);
    }

    @DisplayName("내가 북마크한 작물 게시글을 조회할 수 있다.")
    @Test
    void findAllByMyBookmark() {
        //given
        FindAllMyBookmarkCropPostsRepositoryRequest request = new FindAllMyBookmarkCropPostsRepositoryRequest(0L, 10L);
        CropBookmark cropBookmark = CropBookmark.create(cropPostId, loginUserId);
        cropBookmarkJpaRepository.save(cropBookmark);

        //when & then
        FindAllMyBookmarkCropPostsResponse expected = new FindAllMyBookmarkCropPostsResponse(List.of(new FindAllMyBookmarkCropPostsResponse.CropInfo(cropPostId, cropPost.getTitle(), cropPost.getCropImages().stream().findFirst().get().getImageUrl())));
        assertThat(cropQueryService.findAllByMyBookmark(loginUserId, request)).isEqualTo(expected);
    }

    @DisplayName("내가 구매한 작물 게시글을 조회할 수 있다.")
    @Test
    void findAllMyBoughtCrops() {
        //given
        cropPost.update(
            cropPost.getTitle(),
            cropPost.getContent(),
            cropPost.getCropCategory(),
            cropPost.getPrice(),
            cropPost.getPriceProposal(),
            cropPost.getTradeType(),
            Collections.emptyList(),
            Collections.emptyList(),
            cropPost.getCropPostAuthorId(),
            TradeStatus.TRADED,
            cropPost.getMemberAddressId()
        );
        cropPost.assignBuyer(2L);
        cropJpaRepository.save(cropPost);
        FindAllMyBoughtCropPostsRepositoryRequest request = new FindAllMyBoughtCropPostsRepositoryRequest(2L, 0L, 10L);
        FindAllMyBoughtCropPostsResponse expected = new FindAllMyBoughtCropPostsResponse(List.of(new FindAllMyBoughtCropPostsResponse.CropInfo(cropPostId, cropPost.getTitle(), cropPost.getCropImages().stream().findFirst().get().getImageUrl())));

        //when & then
        assertThat(cropQueryService.findAllMyBoughtCrops(request)).isEqualTo(expected);
    }
}