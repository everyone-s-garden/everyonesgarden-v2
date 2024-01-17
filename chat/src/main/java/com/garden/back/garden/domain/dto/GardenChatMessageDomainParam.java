package com.garden.back.garden.domain.dto;

public record GardenChatMessageDomainParam(
        Long roomId,
        Long memberId,
        String contents
) {
}
