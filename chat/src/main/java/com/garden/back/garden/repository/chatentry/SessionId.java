package com.garden.back.garden.repository.chatentry;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionId sessionId1 = (SessionId) o;
        return Objects.equals(sessionId, sessionId1.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }
}
