package com.garden.back.garden.repository.garden.dto.response;

import org.springframework.data.domain.Pageable;

import java.util.List;

public record GardenLikeByMemberRepositoryResponses(
    List<GardenLikeByMemberRepositoryResponse> response,
    Long nextGardenId,
    boolean hasNext
) {

    private static final Long EMPTY_LAST_ID = 0L;

    public static GardenLikeByMemberRepositoryResponses of(
        List<GardenLikeByMemberRepositoryResponse> response,
        Pageable pageable) {
        return new GardenLikeByMemberRepositoryResponses(
            response,
            getLastId(response),
            hasNext(response, pageable)
        );
    }

    private static Long getLastId(List<GardenLikeByMemberRepositoryResponse> response) {
        if (response.isEmpty()) {
            return EMPTY_LAST_ID;
        }
        return response.get(response.size() - 1).getGardenId();
    }

    private static boolean hasNext(
        List<GardenLikeByMemberRepositoryResponse> response,
        Pageable pageable) {
        return response.size() == pageable.getPageSize();
    }
}
