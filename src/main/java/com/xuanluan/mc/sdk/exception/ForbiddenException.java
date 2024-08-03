package com.xuanluan.mc.sdk.exception;

public class ForbiddenException extends BaseCodeException {

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String code, String message) {
        super(code, message);
    }
}
