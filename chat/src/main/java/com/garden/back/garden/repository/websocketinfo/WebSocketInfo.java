package com.garden.back.garden.repository.websocketinfo;

public record WebSocketInfo(
    String sessionId,
    Long memberId
) {
}
