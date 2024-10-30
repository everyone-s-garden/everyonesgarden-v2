package com.garden.back.garden.domain.dto;

import java.time.LocalDate;

public record MyManagedGardenCreateDomainRequest(
    String myManagedGardenName,
    String myManagedGardenImageUrl,
    LocalDate createdAt,
    Long memberId,
    String description
) {
}
