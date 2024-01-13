package com.garden.back.chat.facade;

import com.garden.back.garden.service.dto.response.GardenChatRoomInfoResult;
import com.garden.back.service.dto.request.ChatRoomEntryResult;

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
            ChatRoomEntryResult chatRoomEntryResult,
            GardenChatRoomInfoResult gardenChatRoomInfo,
            String nickname
    ){
        return new GardenChatRoomEnterFacadeResponse(
                chatRoomEntryResult.partnerId(),
                nickname,
                gardenChatRoomInfo.postId(),
                gardenChatRoomInfo.gardenStatus(),
                gardenChatRoomInfo.gardenName(),
                gardenChatRoomInfo.price(),
                gardenChatRoomInfo.imageUrls()
        );
    }

}
