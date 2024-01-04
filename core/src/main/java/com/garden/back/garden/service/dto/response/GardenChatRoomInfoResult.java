package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.repository.garden.dto.response.GardenChatRoomInfoRepositoryResponse;

import java.util.List;

public record GardenChatRoomInfoResult(
        Long postId,
        String gardenStatus,
        String gardenName,
        String price,
        List<String> imageUrls
) {
    private static final int DISTINCT_RESPONSE_INDEX = 0;
    private static final String DEFAULT_VALUE = "";

    public static GardenChatRoomInfoResult to(
            List<GardenChatRoomInfoRepositoryResponse> responses,
            Long gardenId) {
        if (responses.isEmpty()) {
            return createDefaultResult(gardenId);
        }

        return new GardenChatRoomInfoResult(
                gardenId,
                responses.get(DISTINCT_RESPONSE_INDEX).getGardenStatus().name(),
                responses.get(DISTINCT_RESPONSE_INDEX).getGardenName(),
                responses.get(DISTINCT_RESPONSE_INDEX).getPrice(),
                extractImages(responses)
        );
    }

    private static List<String> extractImages(List<GardenChatRoomInfoRepositoryResponse> responses) {
        return responses.stream()
                .map(GardenChatRoomInfoRepositoryResponse::getImageUrl)
                .toList();
    }

    private static GardenChatRoomInfoResult createDefaultResult(Long gardenId) {
        return new GardenChatRoomInfoResult(gardenId, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, List.of());
    }

}
