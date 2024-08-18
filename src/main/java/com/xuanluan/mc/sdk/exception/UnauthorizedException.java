package com.xuanluan.mc.sdk.exception;

public class UnauthorizedException extends BaseCodeException {
    public UnauthorizedException() {
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String code, String message) {
        super(code, message);
    }
}
