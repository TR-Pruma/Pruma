package com.br.pruma.config;

import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<FieldError> fieldErrors

) {
    public record FieldError(String field, String rejectedValue, String reason){}
}
