package com.garden.back.garden.service;

import com.garden.back.garden.model.Garden;
import com.garden.back.garden.model.GardenImage;
import com.garden.back.garden.repository.garden.GardenRepository;
import com.garden.back.garden.repository.gardenimage.GardenImageRepository;
import com.garden.back.garden.repository.gardenlike.GardenLikeRepository;
import com.garden.back.garden.service.dto.request.GardenByComplexesParam;
import com.garden.back.garden.service.dto.request.GardenDetailParam;
import com.garden.back.garden.service.dto.response.GardenAllResults;
import com.garden.back.garden.service.dto.response.GardenByComplexesResults;
import com.garden.back.garden.service.dto.response.GardenDetailResult;
import com.garden.back.garden.service.dto.response.RecentGardenResults;
import com.garden.back.garden.service.recentview.GardenHistoryManager;
import com.garden.back.garden.service.recentview.RecentViewGarden;
import com.garden.back.garden.service.recentview.RecentViewGardens;
import com.garden.back.testutil.garden.GardenFixture;
import com.garden.back.testutil.garden.GardenImageFixture;
import com.garden.back.testutil.garden.GardenLikeFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@Transactional
@SpringBootTest(webEnvironment = NONE)
public class GardenReadServiceTest {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    @Autowired
    private GardenRepository gardenRepository;

    @Autowired
    private GardenLikeRepository gardenLikeRepository;

    @Autowired
    private GardenImageRepository gardenImageRepository;

    @Autowired
    private GardenReadService gardenReadService;

    @Autowired
    private GardenHistoryManager gardenHistoryManager;

    private Garden savedPrivateGarden;

    @BeforeEach
    void setUp() {
        savedPrivateGarden = gardenRepository.save(GardenFixture.privateGarden());
    }

    @Test
    @DisplayName("모든 텃밭을 조회할 때 해당 텃밭의 좋아요 여부와 사진 등의 정보를 가져올 수 있다.")
    void getAllGardens() {
        // Given
        gardenLikeRepository.save(GardenLikeFixture.gardenLike(savedPrivateGarden));
        GardenImage savedGardenImage = gardenImageRepository.save(GardenImageFixture.gardenImage(savedPrivateGarden));

        // When
        GardenAllResults allGarden = gardenReadService.getAllGarden(GardenFixture.gardenGetAllParam());

        // Then
        allGarden.gardenAllResults()
                .forEach(
                        gardenAllResult -> {
                            assertThat(gardenAllResult.gardenId()).isEqualTo(savedPrivateGarden.getGardenId());
                            assertThat(gardenAllResult.gardenName()).isEqualTo(savedPrivateGarden.getGardenName());
                            assertThat(gardenAllResult.gardenDescription()).isEqualTo(savedPrivateGarden.getGardenDescription());
                            assertThat(gardenAllResult.gardenFacility().isEquipment()).isEqualTo(savedPrivateGarden.getIsEquipment());
                            assertThat(gardenAllResult.gardenFacility().isToilet()).isEqualTo(savedPrivateGarden.getIsToilet());
                            assertThat(gardenAllResult.gardenFacility().isWaterway()).isEqualTo(savedPrivateGarden.getIsWaterway());
                            assertThat(gardenAllResult.gardenStatus()).isEqualTo(savedPrivateGarden.getGardenStatus().name());
                            assertThat(gardenAllResult.gardenType()).isEqualTo(savedPrivateGarden.getGardenType().name());
                            assertThat(gardenAllResult.address()).isEqualTo(savedPrivateGarden.getAddress());
                            assertThat(gardenAllResult.contact()).isEqualTo(savedPrivateGarden.getContact());
                            assertThat(gardenAllResult.isLiked()).isEqualTo(true);
                            assertThat(gardenAllResult.images()).isEqualTo(List.of(savedGardenImage.getImageUrl()));
                            assertThat(gardenAllResult.latitude()).isEqualTo(savedPrivateGarden.getLatitude());
                            assertThat(gardenAllResult.longitude()).isEqualTo(savedPrivateGarden.getLongitude());
                            assertThat(gardenAllResult.size()).isEqualTo(savedPrivateGarden.getSize());
                            assertThat(gardenAllResult.price()).isEqualTo(savedPrivateGarden.getPrice());
                            assertThat(gardenAllResult.linkForRequest()).isEqualTo(savedPrivateGarden.getLinkForRequest());
                            assertThat(gardenAllResult.recruitEndDate()).isEqualTo(savedPrivateGarden.getRecruitEndDate().format(TIME_FORMATTER));
                            assertThat(gardenAllResult.recruitStartDate()).isEqualTo(savedPrivateGarden.getRecruitStartDate().format(TIME_FORMATTER));
                            assertThat(gardenAllResult.useEndDate()).isEqualTo(savedPrivateGarden.getUseEndDate().format(TIME_FORMATTER));
                            assertThat(gardenAllResult.useStartDate()).isEqualTo(savedPrivateGarden.getUseStartDate().format(TIME_FORMATTER));
                        }
                );
    }

    @DisplayName("사용자 위치에 따른 화면 내에 존재하는 텃밭 목록을 올바르게 반환하는지 확인한다.")
    @Test
    void getGardenByComplexes_withinRegions() {
        // Given
        GardenByComplexesParam publicGardenByComplexesParam = GardenFixture.publicGardenByComplexesParam();

        List<Point> points = RandomPointGenerator.generateRandomPoint(publicGardenByComplexesParam);
        points.forEach(
                point -> gardenRepository.save(GardenFixture.randomPublicGardenWithinComplexes(point)));

        // When
        GardenByComplexesResults gardensByComplexes = gardenReadService.getGardensByComplexes(publicGardenByComplexesParam);

        // Then
        assertThat(gardensByComplexes.gardenByComplexesResults().size()).isEqualTo(points.size());
    }

    @DisplayName("텃밭 타입에 맞게 올바르게 조회되는지 확인한다.")
    @Test
    void getGardenByComplexes_gardenType() {
        // Given
        GardenByComplexesParam publicGardenByComplexesParam = GardenFixture.publicGardenByComplexesParam();
        GardenByComplexesParam allGardenByComplexesParam = GardenFixture.allGardenByComplexesParam();
        GardenByComplexesParam privateGardenByComplexesParam = GardenFixture.privateGardenByComplexesParam();

        List<Point> points = RandomPointGenerator.generateRandomPoint(publicGardenByComplexesParam);
        points.forEach(
                point -> gardenRepository.save(GardenFixture.randomPublicGardenWithinComplexes(point)));

        // When
        GardenByComplexesResults publicGardensByComplexes
                = gardenReadService.getGardensByComplexes(publicGardenByComplexesParam);
        GardenByComplexesResults allGardensByComplexes
                = gardenReadService.getGardensByComplexes(allGardenByComplexesParam);
        GardenByComplexesResults privateGardensByComplexes
                = gardenReadService.getGardensByComplexes(privateGardenByComplexesParam);

        // Then
        assertThat(privateGardensByComplexes.gardenByComplexesResults().size()).isEqualTo(0);
        assertThat(allGardensByComplexes.gardenByComplexesResults().size()).isEqualTo(points.size());
        assertThat(publicGardensByComplexes.gardenByComplexesResults().size()).isEqualTo(points.size());
    }

    @DisplayName("텃밭을 조회할 때 텃밭 상세 내용을 확인할 수 있고 동시에 최근 본 텃밭 내역에 포함된다.")
    @Test
    void getGardenDetail() {
        // Given
        GardenDetailParam gardenDetailParam = GardenFixture.gardenDetailParam(savedPrivateGarden.getGardenId());
        gardenImageRepository.save(GardenImageFixture.gardenImage(savedPrivateGarden));

        // When
        GardenDetailResult gardenDetail = gardenReadService.getGardenDetail(gardenDetailParam);
        RecentViewGardens recentViewGardens = gardenHistoryManager.getRecentViewGarden(gardenDetailParam.memberId());
        RecentViewGarden latestViewGarden = recentViewGardens.getRecentViewGardens().recentViewGardens().getFirst();

        // Then
        assertThat(gardenDetail.gardenId()).isEqualTo(savedPrivateGarden.getGardenId());
        assertThat(gardenDetail.gardenDescription()).isEqualTo(savedPrivateGarden.getGardenDescription());
        assertThat(gardenDetail.gardenName()).isEqualTo(savedPrivateGarden.getGardenName());
        assertThat(gardenDetail.gardenStatus()).isEqualTo(savedPrivateGarden.getGardenStatus().name());
        assertThat(gardenDetail.gardenType()).isEqualTo(savedPrivateGarden.getGardenType().name());
        assertThat(gardenDetail.address()).isEqualTo(savedPrivateGarden.getAddress());

        assertThat(RecentViewGarden.to(gardenDetail).gardenId()).isEqualTo(latestViewGarden.gardenId());
        assertThat(RecentViewGarden.to(gardenDetail).gardenName()).isEqualTo(latestViewGarden.gardenName());
        assertThat(RecentViewGarden.to(gardenDetail).gardenStatus()).isEqualTo(latestViewGarden.gardenStatus());
        assertThat(RecentViewGarden.to(gardenDetail).gardenType()).isEqualTo(latestViewGarden.gardenType());
        assertThat(RecentViewGarden.to(gardenDetail).size()).isEqualTo(latestViewGarden.size());
        assertThat(RecentViewGarden.to(gardenDetail).price()).isEqualTo(latestViewGarden.price());
    }

    @DisplayName("최근 본 텃밭 조회 내력을 확인할 수 있다.")
    @Test
    void getRecentGardens() {
        // Given
        GardenDetailParam gardenDetailParam = GardenFixture.gardenDetailParam(savedPrivateGarden.getGardenId());
        gardenImageRepository.save(GardenImageFixture.gardenImage(savedPrivateGarden));

        GardenDetailResult gardenDetail = gardenReadService.getGardenDetail(gardenDetailParam);

        // When
        RecentGardenResults recentGardens = gardenReadService.getRecentGardens(gardenDetailParam.memberId());
        RecentGardenResults.RecentGardenResult latestViewGarden = recentGardens.recentGardenResults().get(0);

        // Then
        assertThat(RecentViewGarden.to(gardenDetail).gardenId()).isEqualTo(latestViewGarden.gardenId());
        assertThat(RecentViewGarden.to(gardenDetail).gardenName()).isEqualTo(latestViewGarden.gardenName());
        assertThat(RecentViewGarden.to(gardenDetail).gardenStatus()).isEqualTo(latestViewGarden.gardenStatus());
        assertThat(RecentViewGarden.to(gardenDetail).gardenType()).isEqualTo(latestViewGarden.gardenType());
        assertThat(RecentViewGarden.to(gardenDetail).size()).isEqualTo(latestViewGarden.size());
        assertThat(RecentViewGarden.to(gardenDetail).price()).isEqualTo(latestViewGarden.price());
    }


}
