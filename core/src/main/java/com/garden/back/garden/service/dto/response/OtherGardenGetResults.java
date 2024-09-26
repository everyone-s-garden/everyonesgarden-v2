package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.domain.vo.GardenStatus;
import com.garden.back.garden.repository.garden.dto.response.OtherGardenRepositoryResponse;
import com.garden.back.garden.repository.garden.dto.response.OtherGardenRepositoryResponses;
import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardensGetRepositoryResponses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record OtherGardenGetResults(

    List<OtherGardenGetResult> otherGardenGetResponse,
    Long nextGardenId,
    boolean hasNext
) {
    public static OtherGardenGetResults to(OtherGardenRepositoryResponses responses) {
        Map<Long, List<String>> imagesPerGardenId = extractImages(responses);
        return new OtherGardenGetResults(
            responses.response().stream()
                .map(response -> OtherGardenGetResult.to(response, imagesPerGardenId.get(response.getGardenId()))).toList(),
            responses.nextGardenId(),
            responses.hasNext()
        );
    }

    public record OtherGardenGetResult(
        Long gardenId,
        String gardenName,
        String price,
        GardenStatus gardenStatus,
        List<String> images,
        boolean isLiked
    ) {

        public static OtherGardenGetResult to(
            OtherGardenRepositoryResponse response,
            List<String> images) {
            return new OtherGardenGetResult(
                response.getGardenId(),
                response.getGardenName(),
                response.getPrice(),
                response.getGardenStatus(),
                images,
                response.getIsLiked()
            );
        }

    }

    private static Map<Long, List<String>> extractImages(OtherGardenRepositoryResponses responses) {
        Map<Long, List<String>> imagesPerGardenId = new HashMap<>();

        responses.response().forEach(response -> {
            Long gardenId = response.getGardenId();
            String imageUrl = response.getImageUrl();

            imagesPerGardenId.computeIfAbsent(gardenId, k -> new ArrayList<>()).add(imageUrl);
        });

        return imagesPerGardenId;
    }

}
