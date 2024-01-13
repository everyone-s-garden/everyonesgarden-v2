package com.garden.back.garden.repository.garden.dto.response;

import com.garden.back.garden.domain.vo.GardenStatus;

public interface GardenChatRoomInfoRepositoryResponse {

    GardenStatus getGardenStatus();
    String getGardenName();
    String getPrice();
    String getImageUrl();
}
