package com.garden.back.garden.dto.request;

import com.garden.back.garden.service.dto.GardenByNameParam;

public record GardenByNameRequest(
        String gardenName,
        Integer pageNumber
) {
    public static GardenByNameParam to(GardenByNameRequest request) {
        isValidPageNumber(request.pageNumber());
        isValidGardenName(request.gardenName());
        return new GardenByNameParam(
                request.gardenName(),
                request.pageNumber()
        );
    }

    private static void isValidGardenName(String gardenName) {
        if (gardenName == null || gardenName.isEmpty()) {
            throw new IllegalArgumentException("텃밭 이름은 null이거나 빈 값일 수 없습니다.");
        }
    }

    private static void isValidPageNumber(Integer pageNumber) {
        if (pageNumber == null) {
            throw new IllegalArgumentException("페이지 수는 null일 수 없습니다.");
        }
        if (pageNumber < 0) {
            throw new IllegalArgumentException("페이지 수는 음수일 수 없습니다.");
        }
    }

}
