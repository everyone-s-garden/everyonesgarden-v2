package com.garden.back.garden.domain.dto;

import java.time.LocalDate;

public record MyManagedGardenUpdateDomainRequest (
        String myManagedGardenImageUrl,
        Long gardenId,
        LocalDate useStartDate,
        LocalDate useEndDate,
        Long memberId
) {
}
