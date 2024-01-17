package com.garden.back.global.exception;

import com.garden.back.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ChatRoomAccessException extends RuntimeException {
    private final ErrorCode errorCode;

    public ChatRoomAccessException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
