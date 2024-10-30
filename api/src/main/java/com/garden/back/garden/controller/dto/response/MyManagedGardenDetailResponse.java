package com.garden.back.garden.controller.dto.response;

import com.garden.back.garden.service.dto.response.MyManagedGardenDetailResult;

import java.util.List;

public record MyManagedGardenDetailResponse(
    Long myManagedGardenId,
    String myManagedGardenName,
    String createdAt,
    List<String> images,
    String description
) {
    public static MyManagedGardenDetailResponse to(MyManagedGardenDetailResult result) {
        return new MyManagedGardenDetailResponse(
            result.myManagedGardenId(),
            result.myManagedGardenName(),
            result.createdAt(),
            List.of(result.imageUrl()),
            result.description()
        );
    }
}
