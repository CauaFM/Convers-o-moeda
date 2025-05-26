package com.backend.conversao.exception;

import lombok.Data;

@Data
public class ApiError {
    private String code;
    private int status;
    private String message;
}
