package com.garden.back.repository.chatentry;

public class SessionId {

    private final String sessionId;

    private SessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public static SessionId of(String sessionId) {
        return new SessionId(
                sessionId
        );
    }
}
