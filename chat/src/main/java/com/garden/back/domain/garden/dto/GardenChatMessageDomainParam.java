package com.garden.back.domain.garden.dto;

public record GardenChatMessageDomainParam(
        Long roomId,
        Long memberId,
        String contents
) {
}
