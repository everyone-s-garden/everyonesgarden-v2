package com.garden.back.garden.facade;

import com.garden.back.garden.facade.dto.GardenDetailFacadeRequest;
import com.garden.back.garden.facade.dto.GardenDetailFacadeResponse;
import com.garden.back.garden.facade.dto.OtherGardenGetFacadeRequest;
import com.garden.back.garden.facade.dto.OtherGardenGetFacadeResponses;
import com.garden.back.garden.service.GardenChatRoomService;
import com.garden.back.garden.service.GardenReadService;
import com.garden.back.garden.service.dto.response.GardenDetailResult;
import com.garden.back.garden.service.dto.response.OtherGardenGetResults;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
        if (isLoggedInUser(request.memberId())) {
            roomId = gardenChatRoomService.getRoomId(request.toGardenChatRoomInfoGetParam());
        }

        return GardenDetailFacadeResponse.to(gardenDetail, roomId);
    }

    public OtherGardenGetFacadeResponses visitOtherGarden(OtherGardenGetFacadeRequest request) {
        OtherGardenGetResults otherGardenGetResults = gardenReadService.visitOtherGarden(request.toOtherGardenGetParam());
        Map<Long, Long> gardenIdToRoomIdMap = new HashMap<>();

        otherGardenGetResults.otherGardenGetResponse().
            forEach(gardenGetResult -> {
                Long roomId = gardenChatRoomService.getRoomId(request.toGardenChatRoomInfoGetParam(gardenGetResult.gardenId()));
                gardenIdToRoomIdMap.put(gardenGetResult.gardenId(), roomId);
            });

        return OtherGardenGetFacadeResponses.of(
            otherGardenGetResults,
            gardenIdToRoomIdMap
        );

    }

    private boolean isLoggedInUser(Long memberId) {
        return !memberId.equals(NOT_LOGIN_USER_ID);
    }
}
