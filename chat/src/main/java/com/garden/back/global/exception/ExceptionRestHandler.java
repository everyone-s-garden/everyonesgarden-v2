package com.garden.back.global.exception;

import com.garden.back.exception.ChatRoomAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class ExceptionRestHandler {

    /**
     * [Exception] 커스텀 예외 - InvalidPostLikeException
     */
    @ExceptionHandler(ChatRoomAccessException.class)
    public ResponseEntity<ErrorResponse> handlePermissionDeniedException(ChatRoomAccessException e) {
        final ErrorResponse response = ErrorResponse.of(e.getErrorCode(), e.getMessage());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    /**
     * [Exception] 서버에 정의되지 않은 모든 예외
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception e) {
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
