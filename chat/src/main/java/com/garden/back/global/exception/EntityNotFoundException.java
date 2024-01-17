package com.garden.back.global.exception;

import com.garden.back.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public EntityNotFoundException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
