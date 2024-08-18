package com.xuanluan.mc.sdk.exception;

public class BadRequestException extends BaseCodeException {
    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String code, String message) {
        super(code, message);
    }
}
