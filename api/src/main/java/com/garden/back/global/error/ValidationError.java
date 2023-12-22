package com.garden.back.global.error;

import lombok.Builder;
import org.springframework.validation.FieldError;

@Builder
public record ValidationError(
        String field,
        String message
) {
    public static ValidationError of(FieldError fieldError) {
        return ValidationError.builder()
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .build();
    }
}