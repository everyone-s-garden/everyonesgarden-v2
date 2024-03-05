package com.garden.back.garden.facade;

import com.garden.back.garden.service.GardenChatRoomService;
import com.garden.back.garden.service.GardenReadService;
import com.garden.back.garden.service.dto.response.GardenDetailResult;
import org.springframework.stereotype.Service;

@Service
public class GardenFacade {
    private static final Long NOT_LOGIN_USER_ID = -1L;
    private static final Long NOT_CREATED_CHAT_ROOM_ID = -1L;
    private final GardenReadService gardenReadService;
    private final GardenChatRoomService gardenChatRoomService;

    public GardenFacade(GardenReadService gardenReadService, GardenChatRoomService gardenChatRoomService) {
        this.gardenReadService = gardenReadService;
        this.gardenChatRoomService = gardenChatRoomService;
    }

    public GardenDetailFacadeResponse getGardenDetail(GardenDetailFacadeRequest request) {
        Long roomId = NOT_CREATED_CHAT_ROOM_ID;
        GardenDetailResult gardenDetail = gardenReadService.getGardenDetail(request.toGardenDetailParam());
        if (!request.memberId().equals(NOT_LOGIN_USER_ID)) {
            roomId = gardenChatRoomService.getRoomId(request.toGardenChatRoomInfoGetParam());
        }

        return GardenDetailFacadeResponse.to(gardenDetail, roomId);
    }
}
