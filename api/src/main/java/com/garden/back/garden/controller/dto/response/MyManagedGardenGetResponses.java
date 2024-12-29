package com.garden.back.garden.controller.dto.response;

import com.garden.back.garden.service.dto.response.MyManagedGardenGetResults;

import java.util.ArrayList;
import java.util.List;

public record MyManagedGardenGetResponses(
    List<MyManagedGardenGetResponse> myManagedGardenGetResponses,
    Long nextMyManagedGardenId,
    boolean hasNext
) {
    public static MyManagedGardenGetResponses to(MyManagedGardenGetResults results) {
        return new MyManagedGardenGetResponses(
            results.myManagedGardenGetResponse().stream()
                .map(MyManagedGardenGetResponse::to)
                .toList(),
            results.nextManagedGardenId(),
            results.hasNext()
        );
    }

    public record MyManagedGardenGetResponse(
        String myManagedGardenName,
        Long myManagedGardenId,
        String createdAt,
        List<String> images,
        String description
    ) {
        public static MyManagedGardenGetResponse to(MyManagedGardenGetResults.MyManagedGardenGetResult result) {
            return new MyManagedGardenGetResponse(
                result.myManagedGardenName(),
                result.myManagedGardenId(),
                result.createdAt(),
                result.images() == null ? new ArrayList<>() : List.of(result.images()),
                result.description()
            );
        }
    }

}
