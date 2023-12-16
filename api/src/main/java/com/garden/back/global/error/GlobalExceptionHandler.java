package com.garden.back.global.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetailCreator.createValidationDetails(e, request, HttpStatus.BAD_REQUEST);

        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleAllException(Exception e, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetailCreator.create(e, request, HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.internalServerError().body(problemDetail);
    }

}
