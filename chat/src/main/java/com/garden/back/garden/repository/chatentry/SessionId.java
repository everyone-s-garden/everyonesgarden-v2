package com.garden.back.garden.repository.chatentry;

import java.util.Objects;

public class SessionId {

    private final String socketSessionId;

    private SessionId(String sessionId) {
        this.socketSessionId = sessionId;
    }

    public static SessionId of(String sessionId) {
        return new SessionId(
                sessionId
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionId sessionId1 = (SessionId) o;
        return Objects.equals(socketSessionId, sessionId1.socketSessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socketSessionId);
    }
}
