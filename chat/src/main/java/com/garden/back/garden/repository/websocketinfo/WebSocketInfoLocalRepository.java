package com.garden.back.garden.repository.websocketinfo;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketInfoLocalRepository {

    Map<String, Long> webSocketInfo = new ConcurrentHashMap<>();

    public void save(String sessionId, Long memberId) {
        webSocketInfo.put(sessionId, memberId);
    }

    public Long getMemberId(String sessionId) {
        return webSocketInfo.get(sessionId);
    }
}
