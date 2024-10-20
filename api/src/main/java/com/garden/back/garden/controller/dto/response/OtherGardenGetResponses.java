package com.garden.back.garden.controller.dto.response;

import com.garden.back.garden.domain.vo.GardenStatus;
import com.garden.back.garden.facade.dto.OtherGardenGetFacadeResponses;
import com.garden.back.garden.service.dto.response.OtherGardenGetResults;

import java.util.List;

public record OtherGardenGetResponses(
    List<OtherGardenGetResponse> otherGardenGetResponse,
    Long nextGardenId,
    boolean hasNext
) {
    public static OtherGardenGetResponses to(OtherGardenGetFacadeResponses results) {
        return new OtherGardenGetResponses(
            results.otherGardenGetResponse().stream()
                .map(OtherGardenGetResponse::to).toList(),
            results.nextGardenId(),
            results.hasNext()
        );
    }



    public record OtherGardenGetResponse(
        Long gardenId,
        String gardenName,
        String price,
        String contact,
        GardenStatus gardenStatus,
        List<String> images,
        Long chatRoomId,
        boolean isLiked
    ) {

        public static OtherGardenGetResponse to(
            OtherGardenGetFacadeResponses.OtherGardenGetFacadeResponse result) {
            return new OtherGardenGetResponse(
                result.gardenId(),
                result.gardenName(),
                result.price(),
                result.contact(),
                result.gardenStatus(),
                result.images(),
                result.chatRoomId(),
                result.isLiked()
            );
        }

    }
}
