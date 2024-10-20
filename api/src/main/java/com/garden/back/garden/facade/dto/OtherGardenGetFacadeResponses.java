package com.garden.back.garden.facade.dto;

import com.garden.back.garden.domain.vo.GardenStatus;
import com.garden.back.garden.service.dto.response.OtherGardenGetResults;

import java.util.List;
import java.util.Map;

public record OtherGardenGetFacadeResponses(
    List<OtherGardenGetFacadeResponse> otherGardenGetResponse,
    Long nextGardenId,
    boolean hasNext
) {

    public static OtherGardenGetFacadeResponses of(
        OtherGardenGetResults results,
        Map<Long, Long> gardenIdsToRoomId) {
        return new OtherGardenGetFacadeResponses(
            results.otherGardenGetResponse()
                .stream()
                .map(result ->
                    OtherGardenGetFacadeResponse.of(
                        result, gardenIdsToRoomId.get(result.gardenId())))
                .toList(),
            results.nextGardenId(),
            results.hasNext()
        );
    }

    public record OtherGardenGetFacadeResponse(
        Long gardenId,
        String gardenName,
        String price,
        String contact,
        GardenStatus gardenStatus,
        List<String> images,
        Long chatRoomId,
        boolean isLiked
    ) {
        public static OtherGardenGetFacadeResponse of(
            OtherGardenGetResults.OtherGardenGetResult result,
            Long chatRoomId
        ) {
            return new OtherGardenGetFacadeResponse(
                result.gardenId(),
                result.gardenName(),
                result.price(),
                result.contact(),
                result.gardenStatus(),
                result.images(),
                chatRoomId,
                result.isLiked()
            );
        }

    }
}
