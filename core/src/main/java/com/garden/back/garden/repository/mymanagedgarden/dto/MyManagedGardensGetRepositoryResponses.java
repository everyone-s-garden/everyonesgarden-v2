package com.garden.back.garden.repository.mymanagedgarden.dto;

import org.springframework.data.domain.Pageable;

import java.util.List;

public record MyManagedGardensGetRepositoryResponses(
    List<MyManagedGardensGetRepositoryResponse> responses,
    Long nextManagedGardenId,
    boolean hasNext
) {
    private static final Long EMPTY_LAST_ID = 0L;

    public static MyManagedGardensGetRepositoryResponses to(
        List<MyManagedGardensGetRepositoryResponse> responses,
        Pageable pageable) {

        return new MyManagedGardensGetRepositoryResponses(
            responses,
            getLastId(responses),
            hasNext(responses, pageable)
        );
    }

    private static Long getLastId(List<MyManagedGardensGetRepositoryResponse> responses) {
        if (responses.isEmpty()) {
            return EMPTY_LAST_ID;
        }
        return responses.get(responses.size() - 1).getMyManagedGardenId();
    }

    private static boolean hasNext(
        List<MyManagedGardensGetRepositoryResponse> responses,
        Pageable pageable) {
        return responses.size() == pageable.getPageSize();
    }

}
