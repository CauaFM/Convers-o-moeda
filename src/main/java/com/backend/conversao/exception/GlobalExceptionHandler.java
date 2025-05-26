package com.backend.conversao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MoedaNotFoundException.class)
    public ResponseEntity<ApiError> handleMoedaNotFoundException(MoedaNotFoundException ex){
        ApiError apiError = new ApiError();
        apiError.setCode("MOEDA-404");
        apiError.setStatus(HttpStatus.NOT_FOUND.value());
        apiError.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex){
        ApiError apiError = new ApiError();
        apiError.setCode("GENERIC-400");
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());
        apiError.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

}
