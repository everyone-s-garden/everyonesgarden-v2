package com.garden.back.chat.facade;

import com.garden.back.garden.service.dto.response.GardenChatRoomInfoResult;
import com.garden.back.service.garden.dto.response.GardenChatRoomEntryResult;

import java.util.List;

public record GardenChatRoomEnterFacadeResponse(
        Long partnerId,
        String partnerNickname,
        Long postId,
        String gardenStatus,
        String gardenName,
        String price,
        List<String> images
) {
    public static GardenChatRoomEnterFacadeResponse to(
            GardenChatRoomEntryResult gardenChatRoomEntryResult,
            GardenChatRoomInfoResult gardenChatRoomInfo,
            String nickname
    ){
        return new GardenChatRoomEnterFacadeResponse(
                gardenChatRoomEntryResult.partnerId(),
                nickname,
                gardenChatRoomInfo.postId(),
                gardenChatRoomInfo.gardenStatus(),
                gardenChatRoomInfo.gardenName(),
                gardenChatRoomInfo.price(),
                gardenChatRoomInfo.imageUrls()
        );
    }

}
