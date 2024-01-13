package com.garden.back.global.error;

import com.garden.back.exception.ChatRoomAccessException;
import com.garden.back.global.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetailCreator.createValidationDetails(e, request, HttpStatus.BAD_REQUEST);

        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleAllException(Exception e, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetailCreator.create(e, request, HttpStatus.BAD_REQUEST);

        return ResponseEntity.badRequest().body(problemDetail);
    }

}
