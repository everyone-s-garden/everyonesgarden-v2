package com.garden.back.garden.controller.dto.response;

import com.garden.back.garden.service.dto.response.OtherManagedGardenGetResults;

import java.util.ArrayList;
import java.util.List;

public record OtherManagedGardenGetResponses(
    List<OtherManagedGardenGetResponse> otherManagedGardenGetResponses,
    Long nextManagedGardenId,
    boolean hasNext
) {
    public static OtherManagedGardenGetResponses to(OtherManagedGardenGetResults results) {
        return new OtherManagedGardenGetResponses(
            results.otherManagedGardenGetResponse().stream()
                .map(OtherManagedGardenGetResponse::to)
                .toList(),
            results.nextManagedGardenId(),
            results.hasNext()
        );
    }

    public record OtherManagedGardenGetResponse(
        Long myManagedGardenId,
        String myManagedGardenName,
        String createdAt,
        List<String> images,
        String description
    ) {
        public static OtherManagedGardenGetResponse to(OtherManagedGardenGetResults.OtherManagedGardenGetResult result) {
            return new OtherManagedGardenGetResponse(
                result.myManagedGardenId(),
                result.myManagedGardenName(),
                result.createdAt(),
                result.image() == null ? new ArrayList<>() : List.of(result.image()),
                result.description()
            );
        }
    }

}
