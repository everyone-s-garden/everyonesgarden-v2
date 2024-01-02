package com.garden.back.service.dto.request;

import com.garden.back.domain.ChatType;

public record ChatRoomEntryParam (
        Long roomId,
        Long memberId,
        ChatType chatType
) {
}
