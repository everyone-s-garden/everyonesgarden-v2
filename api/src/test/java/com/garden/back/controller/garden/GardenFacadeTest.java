package com.garden.back.controller.garden;

import com.garden.back.garden.domain.Garden;
import com.garden.back.garden.facade.GardenDetailFacadeRequest;
import com.garden.back.garden.facade.GardenDetailFacadeResponse;
import com.garden.back.garden.facade.GardenFacade;
import com.garden.back.garden.repository.garden.GardenRepository;
import com.garden.back.garden.service.GardenReadService;
import com.garden.back.global.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class GardenFacadeTest extends IntegrationTestSupport {
    @Autowired
    private GardenFacade gardenFacade;

    @Autowired
    private  GardenReadService gardenReadService;

    @Autowired
    private GardenRepository gardenRepository;

    @DisplayName("텃밭 분양글의 상세보기를 볼 수 있으면 채팅방이 존재하지 않으면 채팅방 아이디는 -1를 반환한다.")
    @Test
    void getGardenDetail() {
        // Given
        Garden garden = GardenFixture.privateGarden();
        Garden savedGarden = gardenRepository.save(garden);
        GardenDetailFacadeRequest gardenDetailFacadeRequest = new GardenDetailFacadeRequest(garden.getWriterId(), savedGarden.getGardenId());

        // When
        GardenDetailFacadeResponse gardenDetail = gardenFacade.getGardenDetail(gardenDetailFacadeRequest);

        // Then
        assertThat(gardenDetail.roomId()).isEqualTo(-1L);
    }

}
