package com.garden.back.garden.repository.mymanagedgarden.dto;

import org.springframework.data.domain.Pageable;

public record MyManagedGardensGetRepositoryParam(
    Long memberId,
    Long nextManagedId,
    Pageable pageable
) {
}
