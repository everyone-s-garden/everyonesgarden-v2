package com.garden.back.garden.facade;

import com.garden.back.garden.service.GardenChatRoomService;
import com.garden.back.garden.service.GardenReadService;
import com.garden.back.garden.service.dto.response.GardenDetailResult;

public class GardenFacade {
    private final GardenReadService gardenReadService;
    private final GardenChatRoomService gardenChatRoomService;

    public GardenFacade(GardenReadService gardenReadService, GardenChatRoomService gardenChatRoomService) {
        this.gardenReadService = gardenReadService;
        this.gardenChatRoomService = gardenChatRoomService;
    }

    public GardenDetailFacadeResponse getGardenDetail(GardenDetailFacadeRequest request) {
        GardenDetailResult gardenDetail = gardenReadService.getGardenDetail(request.toGardenDetailParam());
        Long roomId = gardenChatRoomService.getRoomId(request.toGardenChatRoomInfoGetParam());

        return GardenDetailFacadeResponse.to(gardenDetail, roomId);
    }
}
