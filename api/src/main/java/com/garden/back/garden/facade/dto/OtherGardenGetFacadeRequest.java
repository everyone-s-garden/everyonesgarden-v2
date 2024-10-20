package com.garden.back.garden.facade.dto;

import com.garden.back.garden.service.dto.request.GardenChatRoomInfoGetParam;
import com.garden.back.garden.service.dto.request.OtherGardenGetParam;

public record OtherGardenGetFacadeRequest(
    Long otherMemberIdToVisit,
    Long nextGardenId,
    Long myMemberId
) {

    public OtherGardenGetParam toOtherGardenGetParam() {
        return new OtherGardenGetParam(
            otherMemberIdToVisit,
            nextGardenId,
            myMemberId
        );
    }

    public GardenChatRoomInfoGetParam toGardenChatRoomInfoGetParam(Long gardenId) {
        return new GardenChatRoomInfoGetParam(
            myMemberId,
            gardenId
        );
    }
}
