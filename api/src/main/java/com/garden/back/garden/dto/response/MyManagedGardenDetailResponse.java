package com.garden.back.garden.dto.response;

import com.garden.back.garden.service.dto.response.MyManagedGardenDetailResult;

public record MyManagedGardenDetailResponse(
        Long myManagedGardenId,
        String gardenName,
        String address,
        String useStartDate,
        String useEndDate,
        String imageUrl
) {
    public static MyManagedGardenDetailResponse to(MyManagedGardenDetailResult result) {
        return new MyManagedGardenDetailResponse(
                result.myManagedGardenId(),
                result.gardenName(),
                result.address(),
                result.useStartDate(),
                result.useEndDate(),
                result.imageUrl()
        );
    }
}
