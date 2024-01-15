package com.garden.back.domain.garden.dto;

public record ReadGardenChatMessage(
        Long roomId,
        Long memberId,
        String contents,
        boolean readOrNot
) {
}
