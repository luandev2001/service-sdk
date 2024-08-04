package com.xuanluan.mc.sdk.exception;

public class UnprocessableException extends BaseCodeException {
    public UnprocessableException() {
    }

    public UnprocessableException(String message) {
        super(message);
    }

    public UnprocessableException(String code, String message) {
        super(code, message);
    }
}
