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
    String price,
    String contact,
    String size,
    String gardenStatus,
    Long writerId,
    String recruitStartDate,
    String recruitEndDate,
    String gardenDescription,
    List<String> images,
    String gardenFacilities,
    Long gardenLikeId,
    String openAPIGardenId
) {

    public static GardenDetailResult to(
        List<GardenDetailRepositoryResponse> gardenDetailRepositoryResponses,
        String openAPIGardenId) {

        GardenDetailRepositoryResponse gardenDetailRepositoryResponse =
            getDistinctGardenDetailRepositoryResponse(gardenDetailRepositoryResponses);

        return new GardenDetailResult(
            gardenDetailRepositoryResponse.getGardenId(),
            gardenDetailRepositoryResponse.getAddress(),
            gardenDetailRepositoryResponse.getLatitude(),
            gardenDetailRepositoryResponse.getLongitude(),
            gardenDetailRepositoryResponse.getGardenName(),
            gardenDetailRepositoryResponse.getGardenType().name(),
            gardenDetailRepositoryResponse.getPrice(),
            gardenDetailRepositoryResponse.getContact(),
            gardenDetailRepositoryResponse.getSize(),
            gardenDetailRepositoryResponse.getGardenStatus().name(),
            gardenDetailRepositoryResponse.getWriterId(),
            gardenDetailRepositoryResponse.getRecruitStartDate(),
            gardenDetailRepositoryResponse.getRecruitEndDate(),
            gardenDetailRepositoryResponse.getGardenDescription(),
            gardenDetailRepositoryResponses.stream()
                .map(GardenDetailRepositoryResponse::getImageUrl)
                .toList(),
            gardenDetailRepositoryResponse.getGardenFacilities(),
            gardenDetailRepositoryResponse.getGardenLikeId(),
            openAPIGardenId
        );
    }

    private static GardenDetailRepositoryResponse getDistinctGardenDetailRepositoryResponse(
        List<GardenDetailRepositoryResponse> gardenDetailRepositoryResponses) {

        return gardenDetailRepositoryResponses.stream()
            .distinct()
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException("해당하는 게시물은 존재하지 않습니다."));
    }

}
