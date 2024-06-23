package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardensGetRepositoryResponse;
import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardensGetRepositoryResponses;

import java.time.format.DateTimeFormatter;
import java.util.*;

public record MyManagedGardenGetResults(
    List<MyManagedGardenGetResult> myManagedGardenGetResponse,
    Long nextManagedGardenId,
    boolean hasNext
) {
    public static MyManagedGardenGetResults to(MyManagedGardensGetRepositoryResponses responses) {
        Map<Long, List<String>> imagesPerGardenId = extractImages(responses);
        return new MyManagedGardenGetResults(
            responses.responses().stream()
                .map(response -> MyManagedGardenGetResult.to(response, imagesPerGardenId.get(response.getMyManagedGardenId())))
                .toList(),
            responses.nextManagedGardenId(),
            responses.hasNext()
        );
    }

    public record MyManagedGardenGetResult(
        Long myManagedGardenId,
        String gardenName,
        String useStartDate,
        String useEndDate,
        List<String> images,
        String description
    ) {
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        public static MyManagedGardenGetResult to(
            MyManagedGardensGetRepositoryResponse response,
            List<String> images) {
            return new MyManagedGardenGetResult(
                response.getMyManagedGardenId(),
                response.getGardenName(),
                response.getUseStartDate().format(DATE_FORMATTER),
                response.getUseEndDate().format(DATE_FORMATTER),
                images,
                response.getDescription()
            );
        }

    }

    private static Map<Long, List<String>> extractImages(MyManagedGardensGetRepositoryResponses responses) {
        Map<Long, List<String>> imagesPerGardenId = new HashMap<>();

        responses.responses().forEach(response -> {
            Long gardenId = response.getMyManagedGardenId();
            String imageUrl = response.getImageUrl();

            imagesPerGardenId.computeIfAbsent(gardenId, k -> new ArrayList<>()).add(imageUrl);
        });

        return imagesPerGardenId;
    }
}