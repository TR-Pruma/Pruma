package com.br.pruma.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<ApiError.FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new ApiError.FieldError(
                        err.getField(),
                        String.valueOf(err.getRejectedValue()),
                        err.getDefaultMessage()
                ))
                .toList();

        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                "Um ou mais campos estão inválidos",
                request.getRequestURI(),
                fieldErrors
        );

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleResponseStatus(
            ResponseStatusException ex,
            HttpServletRequest request) {

        int status = ex.getStatusCode().value();
        String error = HttpStatus.resolve(status).getReasonPhrase();
        String message = ex.getReason();

        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                status,
                error,
                message,
                request.getRequestURI(),
                List.of()
        );

        return ResponseEntity.status(status).body(apiError);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        List<ApiError.FieldError> fieldErrors = ex.getConstraintViolations()
                .stream()
                .map(violation -> new ApiError.FieldError(
                        violation.getPropertyPath().toString(),
                        String.valueOf(violation.getInvalidValue()),
                        violation.getMessage()
                ))
                .toList();

        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Constraint Violation",
                "Parâmetros de consulta inválidos",
                request.getRequestURI(),
                fieldErrors
        );

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllUncaught(
            Exception ex,
            HttpServletRequest request) {

        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI(),
                List.of()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
        ) {
            @Override
            public HttpStatusCode getStatusCode() {
                return this.getStatusCode();
            }

            @Override
            public ProblemDetail getBody() {
                return this.getBody();
            }
        };
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
