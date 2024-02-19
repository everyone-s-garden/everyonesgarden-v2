package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.repository.garden.dto.response.RecentCreateGardenRepositoryResponse;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record RecentCreatedGardenResults(
    List<RecentCreatedGardenResult> recentCreatedGardenResults
) {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public static RecentCreatedGardenResults to(List<RecentCreateGardenRepositoryResponse> responses) {
        Map<Long, String> thumbnails = makeThumbnail(responses);
        return new RecentCreatedGardenResults(
            responses.stream()
                .map(response ->
                    RecentCreatedGardenResult.to(response, thumbnails.getOrDefault(response.getGardenId(),"")))
                .toList()
        );
    }

    public record RecentCreatedGardenResult(
        Long gardenId,
        String gardenName,
        String address,
        Double latitude,
        Double longitude,
        String recruitStartDate,
        String recruitEndDate,
        String price,
        boolean isLiked,
        String imageUrl
    ) {
        public static RecentCreatedGardenResult to(
            RecentCreateGardenRepositoryResponse response,
            String thumbnail) {
            return new RecentCreatedGardenResult(
                response.getGardenId(),
                response.getGardenName(),
                response.getAddress(),
                response.getLatitude(),
                response.getLongitude(),
                response.getRecruitStartDate().format(DATE_FORMATTER),
                response.getRecruitEndDate().format(DATE_FORMATTER),
                response.getPrice(),
                response.getIsLiked(),
                thumbnail
            );
        }

    }

    private static Map<Long, String> makeThumbnail(List<RecentCreateGardenRepositoryResponse> responses) {
        Map<Long, String> thumbnails = new HashMap<>();
        responses.forEach(
            response -> thumbnails.computeIfAbsent(response.getGardenId(), value -> response.getImageUrl())
        );
        return thumbnails;
    }
}
