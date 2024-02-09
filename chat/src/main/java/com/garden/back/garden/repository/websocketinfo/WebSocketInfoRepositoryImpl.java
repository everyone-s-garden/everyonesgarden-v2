package com.garden.back.garden.repository.websocketinfo;

import org.springframework.stereotype.Component;

@Component
public class WebSocketInfoRepositoryImpl implements WebSocketInfoRepository{

    private final WebSocketInfoLocalRepository webSocketInfoLocalRepository;

    public WebSocketInfoRepositoryImpl(WebSocketInfoLocalRepository webSocketInfoLocalRepository) {
        this.webSocketInfoLocalRepository = webSocketInfoLocalRepository;
    }

    @Override
    public void save(String sessionId, Long memberId) {
        webSocketInfoLocalRepository.save(sessionId, memberId);
    }

    @Override
    public Long getMemberId(String sessionId) {
        return webSocketInfoLocalRepository.getMemberId(sessionId);
    }
}
