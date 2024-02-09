package com.garden.back.garden.repository.websocketinfo;

public interface WebSocketInfoRepository {

    void save(String sessionId, Long memberId);

    Long getMemberId(String sessionId);
}
