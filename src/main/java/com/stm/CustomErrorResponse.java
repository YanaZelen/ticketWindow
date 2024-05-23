package com.stm;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomErrorResponse {
    private String message;
    private int status;
    private long timestamp;
    private Map<String, String> errors;

    public CustomErrorResponse(String message, int status, Map<String, String> errors) {
        this.message = message;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
        this.errors = errors;
    }
}

