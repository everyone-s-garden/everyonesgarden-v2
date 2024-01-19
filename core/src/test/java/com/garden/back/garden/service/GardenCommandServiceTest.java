package com.garden.back.garden.service;

import com.garden.back.garden.domain.Garden;
import com.garden.back.garden.domain.GardenImage;
import com.garden.back.garden.domain.MyManagedGarden;
import com.garden.back.garden.repository.garden.GardenRepository;
import com.garden.back.garden.repository.garden.dto.response.GardenDetailRepositoryResponse;
import com.garden.back.garden.repository.gardenimage.GardenImageRepository;
import com.garden.back.garden.repository.gardenlike.GardenLikeRepository;
import com.garden.back.garden.repository.mymanagedgarden.MyManagedGardenRepository;
import com.garden.back.garden.service.dto.request.*;
import com.garden.back.garden.service.recentview.GardenHistoryManager;
import com.garden.back.global.IntegrationTestSupport;
import com.garden.back.testutil.garden.GardenFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@Transactional
class GardenCommandServiceTest extends IntegrationTestSupport {

    private static final String EMPTY_IMAGE_URL = "";

    @Autowired
    private GardenRepository gardenRepository;

    @Autowired
    private GardenLikeRepository gardenLikeRepository;

    @Autowired
    private GardenImageRepository gardenImageRepository;

    @Autowired
    private MyManagedGardenRepository myManagedGardenRepository;

    @Autowired
    private GardenCommandService gardenCommandService;

    @Autowired
    private GardenHistoryManager gardenHistoryManager;

    private Garden savedPrivateGarden;

    @BeforeEach
    void setUp() {
        savedPrivateGarden = gardenRepository.save(GardenFixture.privateGarden());
    }

    @DisplayName("텃밭을 삭제할 때 존재하는 텃밭이 아닌 경우 예외를 던진다.")
    @Test
    void deleteGarden_throwException_notExistedGarden() {
        // Given
        GardenDeleteParam notExistedGardenDetailParam = new GardenDeleteParam(
                savedPrivateGarden.getWriterId(),
                savedPrivateGarden.getGardenId() + 100L);

        // When_Then
        assertThatThrownBy(() -> gardenCommandService.deleteGarden(notExistedGardenDetailParam))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("내가 작성한 텃밭 게시물이 아닌 경우 예외를 던진다.")
    @Test
    void deleteGarden_throwException_notWriter() {
        // Given
        GardenDeleteParam notWriterGardenDetailParam = new GardenDeleteParam(
                savedPrivateGarden.getWriterId() + 1L,
                savedPrivateGarden.getGardenId());

        // When_Then
        assertThatThrownBy(() -> gardenCommandService.deleteGarden(notWriterGardenDetailParam))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("내가 작성한 텃밭을 삭제할 수 있으며 삭제할 때 텃밭 이미지도 함께 삭제된다.")
    @Test
    void deleteGarden() {
        // Given
        GardenDeleteParam gardenDeleteParam = new GardenDeleteParam(
                savedPrivateGarden.getWriterId(),
                savedPrivateGarden.getGardenId());

        // When
        gardenCommandService.deleteGarden(gardenDeleteParam);

        // Then
        assertThatThrownBy(() -> gardenRepository.getById(savedPrivateGarden.getGardenId()))
                .isInstanceOf(EmptyResultDataAccessException.class);
        assertThat(gardenImageRepository.findByGardenId(savedPrivateGarden.getGardenId()).size())
                .isEqualTo(0);

    }

    @DisplayName("원하는 텃밭 게시물에 대해서 좋아요를 할 수 있다.")
    @Test
    void createGardenLike() {
        // Then
        GardenLikeCreateParam gardenLikeCreateParam = new GardenLikeCreateParam(2L, savedPrivateGarden.getGardenId());

        // When
        gardenCommandService.createGardenLike(gardenLikeCreateParam);
        List<GardenDetailRepositoryResponse> gardenDetail = gardenRepository.getGardenDetail(2L, savedPrivateGarden.getGardenId());

        // Then
        assertThat(gardenDetail)
                .extracting(
                        "isLiked")
                .contains(true);
    }

    @DisplayName("좋아요한 텃밭 게시물의 좋아요를 취소할 수 있다")
    @Test
    void deleteGardenLike() {
        // Then
        GardenLikeCreateParam gardenLikeCreateParam = new GardenLikeCreateParam(2L, savedPrivateGarden.getGardenId());
        gardenCommandService.createGardenLike(gardenLikeCreateParam);
        GardenLikeDeleteParam gardenLikeDeleteParam = new GardenLikeDeleteParam(2L, savedPrivateGarden.getGardenId());

        // When
        gardenCommandService.deleteGardenLike(gardenLikeDeleteParam);

        // Then
        assertThat(gardenLikeRepository.findAll().size()).isEqualTo(0);
    }

    @DisplayName("내가 분양하고자 하는 텃밭을 등록할 수 있다.")
    @Test
    void createGarden() {
        //Given
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/garden/download.jpg";
        GardenCreateParam gardenCreateParam = GardenFixture.gardenCreateParam(expectedUrl);
        given(parallelImageUploader.upload(any(), (List<MultipartFile>) any())).willReturn(List.of(expectedUrl));

        //When
        Long savedGardenId = gardenCommandService.createGarden(gardenCreateParam);
        Garden garden = gardenRepository.getById(savedGardenId);

        //Then
        assertThat(garden.getAddress()).isEqualTo(gardenCreateParam.address());
        assertThat(garden.getLatitude()).isEqualTo(gardenCreateParam.latitude());
        assertThat(garden.getLongitude()).isEqualTo(gardenCreateParam.longitude());
        assertThat(garden.getGardenStatus()).isEqualTo(gardenCreateParam.gardenStatus());
        assertThat(garden.getGardenDescription()).isEqualTo(gardenCreateParam.gardenDescription());
        assertThat(garden.getSize()).isEqualTo(gardenCreateParam.size());
        assertThat(garden.getGardenName()).isEqualTo(gardenCreateParam.gardenName());
        assertThat(garden.getPrice()).isEqualTo(gardenCreateParam.price());
    }

    @DisplayName("내가 분양하고자 하는 텃밭을 등록할 때 빈 값이 들어오면 이미지는 네이버에 업로드 되지 않고 빈값으로 저장된다.")
    @Test
    void createGarden_imageNull() {
        //Given
        List<String> expectedUrl = Collections.emptyList();
        GardenCreateParam gardenCreateParam = GardenFixture.gardenCreateParam();
        given(parallelImageUploader.upload(any(), (List<MultipartFile>) any())).willReturn(expectedUrl);

        //When
        Long savedGardenId = gardenCommandService.createGarden(gardenCreateParam);
        List<GardenImage> gardenImages = gardenImageRepository.findByGardenId(savedGardenId);

        //Then
        assertThat(gardenImages).isEmpty();
    }

    @DisplayName("텃밭을 수정할 수 있다.")
    @Test
    void updateGarden() {
        //Given
        Garden gardenToSave = GardenFixture.privateGarden();
        Garden savedGarden = gardenRepository.save(gardenToSave);

        GardenImage firstGardenImage = GardenFixture.firstGardenImage(savedGarden);
        GardenImage secondGardenImage = GardenFixture.secondGardenImage(savedGarden);
        gardenImageRepository.save(firstGardenImage);
        gardenImageRepository.save(secondGardenImage);

        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/garden/download.jpg";
        given(imageUploader.upload(any(), any())).willReturn(expectedUrl);
        GardenUpdateParam gardenUpdateParam = GardenFixture.gardenUpdateParam(expectedUrl, savedGarden.getGardenId());

        //When
        Long updatedGardenId = gardenCommandService.updateGarden(gardenUpdateParam);
        Garden updatedGarden = gardenRepository.getById(updatedGardenId);
        List<GardenImage> updatedGardenImages = gardenImageRepository.findByGardenId(updatedGardenId);

        //Then
        assertThat(updatedGarden.getGardenId()).isEqualTo(gardenUpdateParam.gardenId());
        assertThat(updatedGarden.getGardenStatus()).isEqualTo(gardenUpdateParam.gardenStatus());
        assertThat(updatedGarden.getGardenType()).isEqualTo(gardenUpdateParam.gardenType());
        assertThat(updatedGarden.getGardenDescription()).isEqualTo(gardenUpdateParam.gardenDescription());
        assertThat(updatedGarden.getGardenName()).isEqualTo(gardenUpdateParam.gardenName());
        assertThat(updatedGarden.getContact()).isEqualTo(gardenUpdateParam.contact());
        assertThat(updatedGarden.getAddress()).isEqualTo(gardenUpdateParam.address());
        assertThat(updatedGarden.getRecruitStartDate()).isEqualTo(gardenUpdateParam.recruitStartDate());
        assertThat(updatedGarden.getRecruitEndDate()).isEqualTo(gardenUpdateParam.recruitEndDate());
        assertThat(updatedGarden.getUseStartDate()).isEqualTo(gardenUpdateParam.useStartDate());
        assertThat(updatedGarden.getUseEndDate()).isEqualTo(gardenUpdateParam.useEndDate());
        assertThat(updatedGarden.getIsToilet()).isEqualTo(gardenUpdateParam.gardenFacility().isToilet());
        assertThat(updatedGarden.getIsWaterway()).isEqualTo(gardenUpdateParam.gardenFacility().isWaterway());
        assertThat(updatedGarden.getIsEquipment()).isEqualTo(gardenUpdateParam.gardenFacility().isEquipment());

        assertThat(updatedGardenImages).extracting("imageUrl")
                .contains(expectedUrl)
                .contains(gardenUpdateParam.remainGardenImageUrls().toArray());
    }

    @DisplayName("텃밭을 수정할 때 새롭게 등록할 파일을 하나도 올리지 않으면 기존의 파일이 그대로 유지된다.")
    @Test
    void updateGarden_nullNewImages() {
        //Given
        Garden gardenToSave = GardenFixture.privateGarden();
        Garden savedGarden = gardenRepository.save(gardenToSave);

        GardenImage firstGardenImage = GardenFixture.firstGardenImage(savedGarden);
        GardenImage secondGardenImage = GardenFixture.secondGardenImage(savedGarden);
        gardenImageRepository.save(firstGardenImage);
        gardenImageRepository.save(secondGardenImage);

        List<String> expectedUrl = Collections.emptyList();
        given(parallelImageUploader.upload(any(), (List<MultipartFile>) any())).willReturn(expectedUrl);
        GardenUpdateParam gardenUpdateParam = GardenFixture.gardenUpdateParamWithoutImageToDelete(savedGarden.getGardenId());

        //When
        gardenCommandService.updateGarden(gardenUpdateParam);
        List<GardenImage> updatedGardenImages = gardenImageRepository.findByGardenId(savedGarden.getGardenId());

        //Then
        assertThat(updatedGardenImages).extracting("imageUrl")
                        .contains(firstGardenImage.getImageUrl(), secondGardenImage.getImageUrl());
        assertThat(updatedGardenImages.size()).isEqualTo(2);
    }

    @DisplayName("내가 가꾸는 텃밭을 삭제할 수 있다.")
    @Test
    void deleteMyManagedGarden() {
        // Given
        MyManagedGarden myManagedGarden = GardenFixture.myManagedGarden(savedPrivateGarden.getGardenId());
        MyManagedGarden savedMyManagedGarden = myManagedGardenRepository.save(myManagedGarden);
        MyManagedGardenDeleteParam myManagedGardenDeleteParam
                = new MyManagedGardenDeleteParam(
                savedMyManagedGarden.getMyManagedGardenId(),
                savedMyManagedGarden.getMemberId());

        // When
        gardenCommandService.deleteMyManagedGarden(myManagedGardenDeleteParam);

        // Then
        assertThat(myManagedGardenRepository.findById(myManagedGardenDeleteParam.myManagedGardenId())).isEqualTo(Optional.empty());
    }

    @DisplayName("내가 가꾸는 텃밭을 등록할 수 있다.")
    @Test
    void createMyManagedGarden() {
        // Given
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/garden/download.jpg";
        MyManagedGardenCreateParam myManagedGardenCreateParam = GardenFixture.myManagedGardenCreateParam(expectedUrl);
        given(imageUploader.upload(any(), any())).willReturn(expectedUrl);

        // When
        Long myManagedGardenId = gardenCommandService.createMyManagedGarden(myManagedGardenCreateParam);
        Optional<MyManagedGarden> myManagedGarden = myManagedGardenRepository.findById(myManagedGardenId);
        MyManagedGarden savedMyManagedGarden = myManagedGarden.get();

        // Then
        assertThat(savedMyManagedGarden.getGardenId()).isEqualTo(myManagedGardenCreateParam.gardenId());
        assertThat(savedMyManagedGarden.getImageUrl()).isEqualTo(expectedUrl);
        assertThat(savedMyManagedGarden.getMemberId()).isEqualTo(myManagedGardenCreateParam.memberId());
        assertThat(savedMyManagedGarden.getUseStartDate()).isEqualTo(myManagedGardenCreateParam.useStartDate());
        assertThat(savedMyManagedGarden.getUseEndDate()).isEqualTo(myManagedGardenCreateParam.useEndDate());
    }

    @DisplayName("내가 가꾸는 텃밭을 등록할 때 null 값이면 저장되지 않고 url로 빈값이 저장된다.")
    @Test
    void createMyManagedGarden_nullImage() {
        // Given
        String expectedUrl = "";
        MyManagedGardenCreateParam myManagedGardenCreateParam = GardenFixture.myManagedGardenCreateParamWithoutImage();
        given(imageUploader.upload(any(), any())).willReturn(expectedUrl);

        // When
        Long myManagedGardenId = gardenCommandService.createMyManagedGarden(myManagedGardenCreateParam);
        Optional<MyManagedGarden> myManagedGarden = myManagedGardenRepository.findById(myManagedGardenId);
        MyManagedGarden savedMyManagedGarden = myManagedGarden.get();

        // Then
        assertThat(savedMyManagedGarden.getImageUrl()).isEqualTo(expectedUrl);
    }

    @DisplayName("내가 가꾸는 텃밭을 수정할 수 있다.")
    @Test
    void updateMyManagedGarden() {
        // Given
        Garden garden = GardenFixture.publicGarden();
        Garden savedPublicGarden = gardenRepository.save(garden);

        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/garden/view.jpg";
        given(imageUploader.upload(any(), any())).willReturn(expectedUrl);

        MyManagedGarden myManagedGarden = GardenFixture.myManagedGarden(savedPrivateGarden.getGardenId());
        MyManagedGarden savedMyManagedGarden = myManagedGardenRepository.save(myManagedGarden);
        MyManagedGardenUpdateParam myManagedGardenUpdateParam = GardenFixture.myManagedGardenUpdateParam(
                expectedUrl,
                savedPublicGarden.getGardenId(),
                savedMyManagedGarden.getMyManagedGardenId()
        );

        // When
        Long updateMyManagedGardenId = gardenCommandService.updateMyManagedGarden(myManagedGardenUpdateParam);
        MyManagedGarden updatedMyManagedGarden = myManagedGardenRepository.getById(updateMyManagedGardenId);

        // Then
        assertThat(updatedMyManagedGarden.getUseEndDate()).isEqualTo(myManagedGardenUpdateParam.useEndDate());
        assertThat(updatedMyManagedGarden.getUseStartDate()).isEqualTo(myManagedGardenUpdateParam.useStartDate());
        assertThat(updatedMyManagedGarden.getGardenId()).isEqualTo(myManagedGardenUpdateParam.gardenId());
        assertThat(updatedMyManagedGarden.getImageUrl()).isEqualTo(expectedUrl);
    }

    @DisplayName("내가 가꾸는 텃밭을 수정할 때 빈값으로 파일을 보내면 기존 파일이 유지된다.")
    @Test
    void updateMyManagedGarden_nullImage() {
        // Given
        Garden garden = GardenFixture.publicGarden();
        Garden savedPublicGarden = gardenRepository.save(garden);

        MyManagedGarden myManagedGarden = GardenFixture.myManagedGarden(savedPrivateGarden.getGardenId());
        MyManagedGarden savedMyManagedGarden = myManagedGardenRepository.save(myManagedGarden);

        String expectedUrl = "";
        given(imageUploader.upload(any(), any())).willReturn(expectedUrl);

        MyManagedGardenUpdateParam myManagedGardenUpdateParam = GardenFixture.myManagedGardenUpdateParamWithoutImage(
                savedPublicGarden.getGardenId(),
                savedMyManagedGarden.getMyManagedGardenId()
        );

        // When
        Long updateMyManagedGardenId = gardenCommandService.updateMyManagedGarden(myManagedGardenUpdateParam);
        MyManagedGarden updatedMyManagedGarden = myManagedGardenRepository.getById(updateMyManagedGardenId);

        // Then
        assertThat(updatedMyManagedGarden.getImageUrl()).isEqualTo(myManagedGarden.getImageUrl());
    }

}
