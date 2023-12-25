package com.garden.back.garden.service;

import com.garden.back.garden.model.Garden;
import com.garden.back.garden.repository.garden.GardenRepository;
import com.garden.back.garden.repository.garden.dto.response.GardenDetailRepositoryResponse;
import com.garden.back.garden.repository.gardenimage.GardenImageRepository;
import com.garden.back.garden.repository.gardenlike.GardenLikeRepository;
import com.garden.back.garden.service.dto.request.GardenCreateParam;
import com.garden.back.garden.service.dto.request.GardenDeleteParam;
import com.garden.back.garden.service.dto.request.GardenLikeCreateParam;
import com.garden.back.garden.service.dto.request.GardenLikeDeleteParam;
import com.garden.back.garden.service.recentview.GardenHistoryManager;
import com.garden.back.global.IntegrationTestSupport;
import com.garden.back.testutil.garden.GardenFixture;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@Transactional
public class GardenCommandServiceTest extends IntegrationTestSupport {

    @Autowired
    private GardenRepository gardenRepository;

    @Autowired
    private GardenLikeRepository gardenLikeRepository;

    @Autowired
    private GardenImageRepository gardenImageRepository;

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
                .isInstanceOf(EntityNotFoundException.class);
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
                .isInstanceOf(JpaObjectRetrievalFailureException.class);
        assertThat(gardenImageRepository.findByGardenId(savedPrivateGarden.getGardenId()))
                .isEqualTo(Optional.empty());

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
        given(imageUploader.upload(any(), any())).willReturn(expectedUrl);

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

}
