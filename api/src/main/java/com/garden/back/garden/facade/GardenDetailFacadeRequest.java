package com.garden.back.garden.facade;

import com.garden.back.garden.service.dto.request.GardenChatRoomInfoGetParam;
import com.garden.back.garden.service.dto.request.GardenDetailParam;

public record GardenDetailFacadeRequest(
    Long memberId,
    Long gardenId
) {
    public GardenDetailParam toGardenDetailParam() {
        return new GardenDetailParam(
            memberId,
            gardenId
        );
    }

    public GardenChatRoomInfoGetParam toGardenChatRoomInfoGetParam() {
        return new GardenChatRoomInfoGetParam(
            memberId,
            gardenId
        );
    }

}
