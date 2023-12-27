package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardensGetRepositoryResponse;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public record MyManagedGardenGetResults(
        List<MyManagedGardenGetResult> myManagedGardenGetRespons
) {
    public static MyManagedGardenGetResults to(List<MyManagedGardensGetRepositoryResponse> responses) {
        return new MyManagedGardenGetResults(
                responses.stream()
                        .map(MyManagedGardenGetResult::to)
                        .toList()
        );
    }

    public record MyManagedGardenGetResult(
            Long myManagedGardenId,
            String gardenName,
            String useStartDate,
            String useEndDate,
            String imageUrl
    ) {
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.KOREA);

        public static MyManagedGardenGetResult to(MyManagedGardensGetRepositoryResponse response) {
            return new MyManagedGardenGetResult(
                    response.getMyManagedGardenId(),
                    response.getGardenName(),
                    response.getUseStartDate().format(DATE_FORMATTER),
                    response.getUseEndDate().format(DATE_FORMATTER),
                    response.getImageUrl()
            );
        }

    }
}