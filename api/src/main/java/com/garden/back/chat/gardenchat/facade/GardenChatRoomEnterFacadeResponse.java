package com.garden.back.chat.gardenchat.facade;

import com.garden.back.garden.service.dto.response.GardenChatRoomInfoResult;
import com.garden.back.garden.service.dto.response.GardenChatRoomEntryResult;
import com.garden.back.member.service.dto.MemberMyPageResult;

import java.util.List;

public record GardenChatRoomEnterFacadeResponse(
        Long partnerId,
        String partnerNickname,
        String partnerMannerGrade,
        String partnerProfileImage,
        Long postId,
        String gardenStatus,
        String gardenName,
        String price,
        List<String> images
) {
    public static GardenChatRoomEnterFacadeResponse to(
        GardenChatRoomEntryResult gardenChatRoomEntryResult,
        GardenChatRoomInfoResult gardenChatRoomInfo,
        MemberMyPageResult partnerInfo
    ){
        return new GardenChatRoomEnterFacadeResponse(
                gardenChatRoomEntryResult.partnerId(),
                partnerInfo.nickname(),
                partnerInfo.memberMannerGrade(),
                partnerInfo.imageUrl(),
                gardenChatRoomInfo.postId(),
                gardenChatRoomInfo.gardenStatus(),
                gardenChatRoomInfo.gardenName(),
                gardenChatRoomInfo.price(),
                gardenChatRoomInfo.imageUrls()
        );
    }

}
