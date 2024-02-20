package com.garden.back.garden.controller.dto.response;

import com.garden.back.garden.service.dto.response.MyManagedGardenGetResults;

import java.util.List;

public record MyManagedGardenGetResponses(
    List<MyManagedGardenGetResponse> myManagedGardenGetResponses
) {
    public static MyManagedGardenGetResponses to(MyManagedGardenGetResults results) {
        return new MyManagedGardenGetResponses(
            results.myManagedGardenGetRespons().stream()
                .map(MyManagedGardenGetResponse::to)
                .toList()
        );
    }

    public record MyManagedGardenGetResponse(
        Long myManagedGardenId,
        String gardenName,
        String useStartDate,
        String useEndDate,
        List<String> images,
        String description
    ) {
        public static MyManagedGardenGetResponse to(MyManagedGardenGetResults.MyManagedGardenGetResult result) {
            return new MyManagedGardenGetResponse(
                result.myManagedGardenId(),
                result.gardenName(),
                result.useStartDate(),
                result.useEndDate(),
                result.images(),
                result.description()
            );
        }
    }

}
