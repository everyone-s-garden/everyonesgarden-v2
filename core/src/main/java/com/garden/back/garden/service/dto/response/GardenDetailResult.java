package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.repository.garden.dto.response.GardenDetailRepositoryResponse;

import java.time.format.DateTimeFormatter;
import java.util.List;

public record GardenDetailResult(
        Long gardenId,
        String address,
        Double latitude,
        Double longitude,
        String gardenName,
        String gardenType,
        String linkForRequest,
        String price,
        String contact,
        String size,
        String gardenStatus,
        Long writerId,
        String recruitStartDate,
        String recruitEndDate,
        String useStartDate,
        String useEndDate,
        String gardenDescription,
        List<String> images,
        GardenFacility gardenFacility,
        boolean isLiked
) {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public static GardenDetailResult to(List<GardenDetailRepositoryResponse> gardenDetailRepositoryResponses) {
        GardenDetailRepositoryResponse gardenDetailRepositoryResponse = gardenDetailRepositoryResponses.get(0);
        return new GardenDetailResult(
                gardenDetailRepositoryResponse.getGardenId(),
                gardenDetailRepositoryResponse.getAddress(),
                gardenDetailRepositoryResponse.getLatitude(),
                gardenDetailRepositoryResponse.getLongitude(),
                gardenDetailRepositoryResponse.getGardenName(),
                gardenDetailRepositoryResponse.getGardenType().name(),
                gardenDetailRepositoryResponse.getLinkForRequest(),
                gardenDetailRepositoryResponse.getPrice(),
                gardenDetailRepositoryResponse.getContact(),
                gardenDetailRepositoryResponse.getSize(),
                gardenDetailRepositoryResponse.getGardenStatus().name(),
                gardenDetailRepositoryResponse.getWriterId(),
                gardenDetailRepositoryResponse.getRecruitStartDate().format(TIME_FORMATTER),
                gardenDetailRepositoryResponse.getRecruitEndDate().format(TIME_FORMATTER),
                gardenDetailRepositoryResponse.getUseStartDate().format(TIME_FORMATTER),
                gardenDetailRepositoryResponse.getUseEndDate().format(TIME_FORMATTER),
                gardenDetailRepositoryResponse.getGardenDescription(),
                gardenDetailRepositoryResponses.stream()
                        .map(GardenDetailRepositoryResponse::getImageUrl)
                        .toList(),
                new GardenFacility(
                        gardenDetailRepositoryResponse.getIsToilet(),
                        gardenDetailRepositoryResponse.getIsWaterway(),
                        gardenDetailRepositoryResponse.getIsEquipment()
                ),
                gardenDetailRepositoryResponse.getIsLiked()
        );
    }

    public record GardenFacility(
            boolean isToilet,
            boolean isWaterway,
            boolean isEquipment
    ) {

    }
}
