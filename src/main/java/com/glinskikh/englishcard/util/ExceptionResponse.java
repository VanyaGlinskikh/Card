package com.glinskikh.englishcard.util;

import lombok.Data;

@Data
public class ExceptionResponse {
    private String message;
    private long timestamp;

    public ExceptionResponse (String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

}
