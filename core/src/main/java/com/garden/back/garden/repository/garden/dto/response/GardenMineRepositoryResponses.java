package com.garden.back.garden.repository.garden.dto.response;


import org.springframework.data.domain.Pageable;

import java.util.List;

public record GardenMineRepositoryResponses(
    List<GardenMineRepositoryResponse> response,
    Long nextGardenId,
    boolean hasNext
) {
    public static GardenMineRepositoryResponses of(
        List<GardenMineRepositoryResponse> response,
        Pageable pageable) {
        return new GardenMineRepositoryResponses(
            response,
            getLastId(response),
            hasNext(response, pageable)
        );
    }

    private static Long getLastId(List<GardenMineRepositoryResponse> response) {
        return response.get(response.size() - 1).getGardenId();
    }

    private static boolean hasNext(
        List<GardenMineRepositoryResponse> response,
        Pageable pageable) {
        return response.size() == pageable.getPageSize();
    }

}
