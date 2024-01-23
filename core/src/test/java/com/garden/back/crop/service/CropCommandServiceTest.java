package com.garden.back.crop.service;

import com.garden.back.crop.domain.CropCategory;
import com.garden.back.crop.domain.CropPost;
import com.garden.back.crop.domain.TradeStatus;
import com.garden.back.crop.domain.TradeType;
import com.garden.back.crop.domain.repository.CropJpaRepository;
import com.garden.back.crop.infra.MonthlyRecommendedCropsInfraResponse;
import com.garden.back.crop.service.request.AssignBuyerServiceRequest;
import com.garden.back.crop.service.request.CreateCropsPostServiceRequest;
import com.garden.back.crop.service.request.UpdateCropsPostServiceRequest;
import com.garden.back.crop.service.response.MonthlyRecommendedCropsResponse;
import com.garden.back.global.IntegrationTestSupport;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@Transactional
class CropCommandServiceTest extends IntegrationTestSupport {

    @Autowired
    CropCommandService cropCommandService;

    @Autowired
    CropJpaRepository cropJpaRepository;

    @DisplayName("매달 추천 작물을 받는다.")
    @Test
    void getMonthlyRecommendedCrops() {
        // given
        int sizeOfMonth = 12;
        MonthlyRecommendedCropsInfraResponse infraResponse = sut.giveMeBuilder(MonthlyRecommendedCropsInfraResponse.class)
            .size("cropsResponses", sizeOfMonth)
            .set("cropsResponses[0].month", 1)
            .set("cropsResponses[1].month", 2)
            .set("cropsResponses[2].month", 3)
            .set("cropsResponses[3].month", 4)
            .set("cropsResponses[4].month", 5)
            .set("cropsResponses[5].month", 6)
            .set("cropsResponses[6].month", 7)
            .set("cropsResponses[7].month", 8)
            .set("cropsResponses[8].month", 9)
            .set("cropsResponses[9].month", 10)
            .set("cropsResponses[10].month", 11)
            .set("cropsResponses[11].month", 12)
            .sample();

        given(monthlyRecommendedCropsFetcher.getMonthlyRecommendedCrops()).willReturn(infraResponse);

        // when
        MonthlyRecommendedCropsResponse actualResponse = cropCommandService.getMonthlyRecommendedCrops();

        // then
        then(actualResponse)
            .as("검증할 응답 객체")
            .isNotNull()
            .satisfies(response -> {
                then(response.cropsResponses())
                    .as("작물 응답 리스트")
                    .hasSize(12)
                    .allSatisfy(cropResponse ->
                        then(cropResponse.month()).isBetween(1, 12));
            });
    }

    @DisplayName("작물 게시글을 생성한다.")
    @Test
    void createCropsPost() {
        //given
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        Long loginUserId = 1L;

        given(imageUploader.upload(any(), any())).willReturn(expectedUrl);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
            "images",
            "image1.png",
            "image/png",
            "image-files".getBytes()
        );

        CreateCropsPostServiceRequest request = sut.giveMeBuilder(CreateCropsPostServiceRequest.class)
            .set("content", "내용")
            .set("title", "제목")
            .set("cropCategory", CropCategory.FRUIT)
            .set("price", 10000)
            .set("priceProposal", false)
            .set("tradeType", TradeType.DIRECT_TRADE)
            .size("images", 1)
            .set("images[0]", mockMultipartFile)
            .sample();

        //when
        Long savedCropId = cropCommandService.createCropsPost(request, loginUserId);

        //then
        CropPost savedCropPost = cropJpaRepository.findById(savedCropId).orElseThrow(() -> new AssertionError("게시글 조회 실패"));
        assertThat(savedCropPost)
            .extracting("title", "content", "cropCategory", "price", "priceProposal", "tradeType")
            .containsExactly(request.title(), request.content(), request.cropCategory(), request.price(), request.priceProposal(), request.tradeType());
    }

    @DisplayName("작물 게시글을 수정한다.")
    @Test
    void updateCropsPost() {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
            "images",
            "image1.png",
            "image/png",
            "image-files".getBytes()
        );

        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        Long loginUserId = 1L;
        CropPost cropPost = CropPost.create("내용", "제목", CropCategory.FRUIT, 100000, false, TradeType.DIRECT_TRADE, List.of(expectedUrl), loginUserId, 1L);
        Long savedCropPostId = cropJpaRepository.save(cropPost).getId();

        UpdateCropsPostServiceRequest request = sut.giveMeBuilder(UpdateCropsPostServiceRequest.class)
            .set("content", "내용2")
            .set("title", "제목2")
            .set("cropCategory", CropCategory.FRUIT)
            .set("price", 10000)
            .set("priceProposal", false)
            .set("tradeType", TradeType.DIRECT_TRADE)
            .size("images", 1)
            .set("images[0]", mockMultipartFile)
            .size("deletedImages", 1)
            .set("deletedImages[0]", expectedUrl)
            .set("memberAddressId", 1L)
            .sample();
        given(imageUploader.upload(any(), any())).willReturn(expectedUrl);

        //when
        cropCommandService.updateCropsPost(savedCropPostId, request, loginUserId);

        //then
        CropPost savedCropPost = cropJpaRepository.findById(savedCropPostId).orElseThrow(() -> new AssertionError("게시글 조회 실패"));
        assertThat(savedCropPost)
            .extracting("title", "content", "cropCategory", "price", "priceProposal", "tradeType")
            .containsExactly(request.title(), request.content(), request.cropCategory(), request.price(), request.priceProposal(), request.tradeType());
    }

    @DisplayName("작물 게시글에 10개가 넘는 이미지를 넣을 수 없다.")
    @Test
    void updateCropsPostInvalid() {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
            "images",
            "image1.png",
            "image/png",
            "image-files".getBytes()
        );

        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        Long loginUserId = 1L;
        CropPost cropPost = CropPost.create("내용", "제목", CropCategory.FRUIT, 100000, false, TradeType.DIRECT_TRADE, List.of(expectedUrl), loginUserId, 1L);
        Long savedCropPostId = cropJpaRepository.save(cropPost).getId();

        UpdateCropsPostServiceRequest request = sut.giveMeBuilder(UpdateCropsPostServiceRequest.class)
            .set("content", "내용2")
            .set("title", "제목2")
            .set("cropCategory", CropCategory.FRUIT)
            .set("price", 10000)
            .set("priceProposal", false)
            .set("tradeType", TradeType.DIRECT_TRADE)
            .size("images", 11)
            .set("images[0]", mockMultipartFile)
            .set("images[1]", mockMultipartFile)
            .set("images[2]", mockMultipartFile)
            .set("images[3]", mockMultipartFile)
            .set("images[4]", mockMultipartFile)
            .set("images[5]", mockMultipartFile)
            .set("images[6]", mockMultipartFile)
            .set("images[7]", mockMultipartFile)
            .set("images[8]", mockMultipartFile)
            .set("images[9]", mockMultipartFile)
            .set("images[10]", mockMultipartFile)
            .size("deletedImages", 1)
            .set("deletedImages[0]", expectedUrl)
            .set("memberAddressId", 1L)
            .sample();

        given(imageUploader.upload(any(), any())).willReturn(expectedUrl);

        //when & then
        assertThrows(IllegalArgumentException.class, () -> {
            cropCommandService.updateCropsPost(savedCropPostId, request, loginUserId);
        }, "게시글 1개에는 10개의 이미지만 등록할 수 있습니다.");
    }

    @DisplayName("작물 게시글을 북마크한다.")
    @Test
    void addCropsBookmark() {
        //given
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        Long loginUserId = 1L;
        CropPost cropPost = CropPost.create("내용", "제목", CropCategory.FRUIT, 100000, false, TradeType.DIRECT_TRADE, List.of(expectedUrl), loginUserId, 1L);
        Long savedCropPostId = cropJpaRepository.save(cropPost).getId();

        //when
        cropCommandService.addCropsBookmark(savedCropPostId, loginUserId);

        //then
        CropPost savedCropPost = cropJpaRepository.findById(savedCropPostId).orElseThrow(() -> new AssertionError("게시글 조회 실패"));
        assertThat(savedCropPost.getBookMarkCount()).isEqualTo(cropPost.getBookMarkCount() + 1);
    }

    @DisplayName("동일한 작물 게시글을 북마크할 수 없다.")
    @Disabled("단일로는 통과가 되는데 통합으로 안됨 아직 이유를 못찾음")
    @Test
    void addCropsBookmarkInvalid() {
        //given
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        Long loginUserId = 1L;
        CropPost cropPost = CropPost.create("내용", "제목", CropCategory.FRUIT, 100000, false, TradeType.DIRECT_TRADE, List.of(expectedUrl), loginUserId, 1L);
        Long savedCropPostId = cropJpaRepository.save(cropPost).getId();
        cropCommandService.addCropsBookmark(savedCropPostId, loginUserId);

        //when & then
        assertThrows(IllegalArgumentException.class, () -> {
            cropCommandService.addCropsBookmark(savedCropPostId, loginUserId);
        }, "같은 사용자가 동일한 게시물에 북마크를 추가할 수 없습니다.");
    }

    @DisplayName("작물 게시글을 삭제한다.")
    @Test
    void deleteCropsPost() {
        //given
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        Long loginUserId = 1L;
        CropPost cropPost = CropPost.create("내용", "제목", CropCategory.FRUIT, 100000, false, TradeType.DIRECT_TRADE, List.of(expectedUrl), loginUserId, 1L);
        Long savedCropPostId = cropJpaRepository.save(cropPost).getId();

        //when
        cropCommandService.deleteCropsPost(savedCropPostId, loginUserId);

        //then
        assertThat(cropJpaRepository.findById(savedCropPostId)).isEmpty();
    }

    @DisplayName("작물 게시글 북마크를 삭제한다.")
    @Test
    void deleteCropsBookmark() {
        //given
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        Long loginUserId = 1L;
        CropPost cropPost = CropPost.create("내용", "제목", CropCategory.FRUIT, 100000, false, TradeType.DIRECT_TRADE, List.of(expectedUrl), loginUserId, 1L);
        Long savedCropPostId = cropJpaRepository.save(cropPost).getId();
        cropCommandService.addCropsBookmark(savedCropPostId, loginUserId);

        //when
        cropCommandService.deleteCropsBookmark(savedCropPostId, loginUserId);

        //then
        CropPost savedCropPost = cropJpaRepository.findById(savedCropPostId).orElseThrow(() -> new AssertionError("게시글 조회 실패"));
        assertThat(savedCropPost.getBookMarkCount()).isZero();
    }

    @DisplayName("작물의 구매자를 할당한다.")
    @Test
    void assignCropBuyer() {
        //given
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        Long loginUserId = 1L;
        CropPost cropPost = CropPost.create("내용", "제목", CropCategory.FRUIT, 100000, false, TradeType.DIRECT_TRADE, List.of(expectedUrl), loginUserId, 1L);
        cropPost.update("제목","내용",CropCategory.FRUIT,100000,false,TradeType.DIRECT_TRADE,List.of(expectedUrl),List.of(expectedUrl),loginUserId, TradeStatus.TRADED, 1L);
        Long savedCropPostId = cropJpaRepository.save(cropPost).getId();
        AssignBuyerServiceRequest request = new AssignBuyerServiceRequest(2L);

        //when
        cropCommandService.assignCropBuyer(savedCropPostId, loginUserId, request);

        //then
        CropPost savedCropPost = cropJpaRepository.findById(savedCropPostId).orElseThrow(() -> new AssertionError("게시글 조회 실패"));
        assertThat(savedCropPost.getBuyerId()).isEqualTo(request.memberId());
    }

    @DisplayName("거래가 완료되지 않은 작물은 구매자를 할당할 수 없다.")
    @Test
    void assignCropBuyerInvalid() {
        //given
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        Long loginUserId = 1L;
        CropPost cropPost = CropPost.create("내용", "제목", CropCategory.FRUIT, 100000, false, TradeType.DIRECT_TRADE, List.of(expectedUrl), loginUserId, 1L);
        Long savedCropPostId = cropJpaRepository.save(cropPost).getId();
        AssignBuyerServiceRequest request = new AssignBuyerServiceRequest(1L);

        //when & then
        assertThrows(IllegalArgumentException.class, () -> {
            cropCommandService.assignCropBuyer(savedCropPostId, loginUserId, request);
        }, "거래 완료가 되지 않은 작물 게시글입니다.");
    }
}