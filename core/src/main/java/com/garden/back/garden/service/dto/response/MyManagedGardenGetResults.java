package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardensGetRepositoryResponse;
import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardensGetRepositoryResponses;

import java.time.format.DateTimeFormatter;
import java.util.*;

public record MyManagedGardenGetResults(
    List<MyManagedGardenGetResult> myManagedGardenGetResponse,
    Long nextManagedGardenId,
    boolean hasNext
) {
    public static MyManagedGardenGetResults to(MyManagedGardensGetRepositoryResponses responses) {
        return new MyManagedGardenGetResults(
            responses.responses().stream()
                .map(response -> MyManagedGardenGetResult.to(response, response.getImageUrl()))
                .toList(),
            responses.nextManagedGardenId(),
            responses.hasNext()
        );
    }

    public record MyManagedGardenGetResult(
        String myManagedGardenName,
        Long myManagedGardenId,
        String createdAt,
        String images,
        String description
    ) {
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        public static MyManagedGardenGetResult to(
            MyManagedGardensGetRepositoryResponse response,
            String images) {
            return new MyManagedGardenGetResult(
                response.getMyManagedGardenName(),
                response.getMyManagedGardenId(),
                response.getCreatedAt().format(DATE_FORMATTER),
                images,
                response.getDescription()
            );
        }

    }

}
