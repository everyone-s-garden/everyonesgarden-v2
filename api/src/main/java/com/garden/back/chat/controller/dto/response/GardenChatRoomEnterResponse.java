package com.garden.back.chat.controller.dto.response;

import com.garden.back.chat.facade.GardenChatRoomEnterFacadeResponse;

import java.util.List;

public record GardenChatRoomEnterResponse(
        Long partnerId,
        String partnerNickname,
        Long postId,
        String gardenStatus,
        String gardenName,
        String price,
        List<String> images
) {
    public static GardenChatRoomEnterResponse to(GardenChatRoomEnterFacadeResponse response){
        return new GardenChatRoomEnterResponse(
                response.partnerId(),
                response.partnerNickname(),
                response.postId(),
                response.gardenStatus(),
                response.gardenName(),
                response.price(),
                response.images()
        );
    }

}
