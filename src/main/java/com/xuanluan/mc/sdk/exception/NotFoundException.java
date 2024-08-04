package com.xuanluan.mc.sdk.exception;

public class NotFoundException extends BaseCodeException {
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String code, String message) {
        super(code, message);
    }
}
