package com.garden.back.garden.service;

import com.garden.back.garden.model.Garden;
import com.garden.back.garden.model.GardenImage;
import com.garden.back.garden.repository.garden.GardenRepository;
import com.garden.back.garden.repository.gardenimage.GardenImageRepository;
import com.garden.back.garden.repository.gardenlike.GardenLikeRepository;
import com.garden.back.garden.service.dto.response.GardenAllResults;
import com.garden.back.testutil.garden.GardenFixture;
import com.garden.back.testutil.garden.GardenImageFixture;
import com.garden.back.testutil.garden.GardenLikeFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@Transactional
@Import(TestDatabaseConfig.class)
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

}
