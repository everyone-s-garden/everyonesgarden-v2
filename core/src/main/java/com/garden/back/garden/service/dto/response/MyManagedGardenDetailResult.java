package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardenDetailRepositoryResponse;

import java.time.format.DateTimeFormatter;

public record MyManagedGardenDetailResult(
        Long myManagedGardenId,
        String gardenName,
        String address,
        String useStartDate,
        String useEndDate,
        String imageUrl
) {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public static MyManagedGardenDetailResult to(MyManagedGardenDetailRepositoryResponse response) {
        return new MyManagedGardenDetailResult(
                response.getMyManagedGardenId(),
                response.getGardenName(),
                response.getAddress(),
                response.getUseStartDate().format(DATE_FORMATTER),
                response.getUseEndDate().format(DATE_FORMATTER),
                response.getImageUrl()
        );
    }
}
