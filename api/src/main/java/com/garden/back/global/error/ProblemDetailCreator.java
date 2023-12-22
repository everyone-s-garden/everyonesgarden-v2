package com.garden.back.global.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.net.URI;

public class ProblemDetailCreator {

    private ProblemDetailCreator() {}

    public static ProblemDetail create(Exception e, HttpServletRequest request, HttpStatus status) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, e.getMessage());
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setTitle(e.getClass().getSimpleName());
        return problemDetail;
    }

    public static ProblemDetail createValidationDetails(MethodArgumentNotValidException e, HttpServletRequest request, HttpStatus status) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, e.getMessage());
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setTitle(e.getClass().getSimpleName());

        problemDetail.setProperty("validationError", e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ValidationError::of)
                .toList());

        return problemDetail;
    }
}