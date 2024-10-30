package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardenDetailRepositoryResponse;

import java.time.format.DateTimeFormatter;

public record MyManagedGardenDetailResult(
    Long myManagedGardenId,
    String myManagedGardenName,
    String createdAt,
    String imageUrl,
    String description
) {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public static MyManagedGardenDetailResult to(MyManagedGardenDetailRepositoryResponse response) {
        return new MyManagedGardenDetailResult(
            response.getMyManagedGardenId(),
            response.getMyManagedGardenName(),
            response.getCreatedAt().format(DATE_FORMATTER),
            response.getImageUrl(),
            response.getDescription()
        );
    }
}
