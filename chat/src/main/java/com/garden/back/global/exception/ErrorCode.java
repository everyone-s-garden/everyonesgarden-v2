package com.garden.back.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode{
    //global
    INTERNAL_SERVER_ERROR("G001", "Internal Server Error"),

    CHAT_ROOM_ACCESS_ERROR("C001","해당 사용자는 이 채팅방에 소속되어 있지 않습니다.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
