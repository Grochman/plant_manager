package com.example.plant_manager.servlet.exceptions;

import lombok.Getter;
public class HttpRequestException extends RuntimeException {

    @Getter
    private final int responseCode;
    public HttpRequestException(int responseCode) {
        this.responseCode = responseCode;
    }
    public HttpRequestException(String message, int responseCode) {
        super(message);
        this.responseCode = responseCode;
    }
}
