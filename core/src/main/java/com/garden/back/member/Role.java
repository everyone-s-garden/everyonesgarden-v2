package com.garden.back.member;

import lombok.Getter;

@Getter
public enum Role {

    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;

    Role(String key, String title) {
        this.key = key;
        this.title = title;
    }
}
