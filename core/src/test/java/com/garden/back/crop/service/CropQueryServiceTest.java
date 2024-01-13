package com.garden.back.crop.service;

import com.garden.back.crop.CropQueryService;
import com.garden.back.crop.FindAllCropsPostResponse;
import com.garden.back.crop.FindCropsPostDetailsResponse;
import com.garden.back.crop.domain.CropCategory;
import com.garden.back.crop.domain.CropPost;
import com.garden.back.crop.domain.TradeType;
import com.garden.back.crop.domain.repository.CropJpaRepository;
import com.garden.back.crop.domain.repository.request.FindAllCropsPostRepositoryRequest;
import com.garden.back.global.IntegrationTestSupport;
import com.garden.back.member.Member;
import com.garden.back.member.Role;
import com.garden.back.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
    Long cropPostId;
    CropPost cropPost;
    String expectedUrl;
    @BeforeEach
    void setUp() {
        expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        Long loginUserId = 1L;
        cropPost = CropPost.create("내용", "제목", CropCategory.FRUIT, 100000, false, TradeType.DIRECT_TRADE, List.of(expectedUrl), loginUserId);
        cropPostId = cropJpaRepository.save(cropPost).getId();
        CropPost deliveryTradePost = CropPost.create("내용2", "제목2", CropCategory.VEGETABLE, 200000, true, TradeType.DELIVERY_TRADE, List.of(expectedUrl), loginUserId);
        cropJpaRepository.save(deliveryTradePost);
    }


    @ParameterizedTest
    @MethodSource("provideRequests")
    void findAll(FindAllCropsPostRepositoryRequest request) {
        FindAllCropsPostResponse response = cropQueryService.findAll(request);
        assertThat(response.cropsInfos()).allMatch(info -> info.tradeType().equals(request.tradeType()))
            .allMatch(info -> info.cropCategory().equals(request.cropCategory()))
            .allMatch(info -> info.title().contains(request.searchContent()));


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
                        0, 10, "제목", cropCategory, tradeType, orderBy))
                )
            );
    }

    @Test
    void findCropsPostDetails() {
        String email = "abc@naver.com";
        String nickname = "asdf";
        Member member = Member.create(email, nickname, Role.USER);
        memberRepository.save(member);

        FindCropsPostDetailsResponse response = sut.giveMeBuilder(FindCropsPostDetailsResponse.class)
            .set("content", cropPost.getContent())
            .set("author", nickname)
            .set("mannerPoint", member.getMannerScore())
            .set("authorAddress", member.getAddress())
            .set("cropCategory", cropPost.getCropCategory())
            .set("bookmarkCount", cropPost.getBookMarkCount())
            .size("images", 1)
            .set("images[0]", expectedUrl)
            .sample();
        assertThat(cropQueryService.findCropsPostDetails(cropPostId)).isEqualTo(response);

    }
}