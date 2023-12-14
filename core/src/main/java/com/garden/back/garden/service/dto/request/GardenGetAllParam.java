package com.garden.back.garden.service.dto.request;

public record GardenGetAllParam(
        Integer pageNumber,
        Long memberId
) {
}
