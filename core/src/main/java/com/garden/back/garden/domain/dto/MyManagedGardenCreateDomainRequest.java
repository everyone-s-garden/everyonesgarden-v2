package com.garden.back.garden.domain.dto;

import java.time.LocalDate;

public record MyManagedGardenCreateDomainRequest(
    String myManagedGardenImageUrl,
    Long gardenId,
    LocalDate useStartDate,
    LocalDate useEndDate,
    Long memberId,
    String description
) {
}
