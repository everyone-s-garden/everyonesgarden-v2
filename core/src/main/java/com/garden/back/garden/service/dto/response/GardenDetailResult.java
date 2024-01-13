package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.repository.garden.dto.response.GardenDetailRepositoryResponse;
import jakarta.persistence.EntityNotFoundException;

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
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public static GardenDetailResult to(List<GardenDetailRepositoryResponse> gardenDetailRepositoryResponses) {
        GardenDetailRepositoryResponse gardenDetailRepositoryResponse =
                getDistinctGardenDetailRepositoryResponse(gardenDetailRepositoryResponses);

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
                gardenDetailRepositoryResponse.getRecruitStartDate().format(DATE_FORMATTER),
                gardenDetailRepositoryResponse.getRecruitEndDate().format(DATE_FORMATTER),
                gardenDetailRepositoryResponse.getUseStartDate().format(DATE_FORMATTER),
                gardenDetailRepositoryResponse.getUseEndDate().format(DATE_FORMATTER),
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

    private static GardenDetailRepositoryResponse getDistinctGardenDetailRepositoryResponse(
            List<GardenDetailRepositoryResponse> gardenDetailRepositoryResponses) {

        return gardenDetailRepositoryResponses.stream()
                .distinct()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("해당하는 게시물은 존재하지 않습니다."));
    }

    public record GardenFacility(
            boolean isToilet,
            boolean isWaterway,
            boolean isEquipment
    ) {

    }
}
