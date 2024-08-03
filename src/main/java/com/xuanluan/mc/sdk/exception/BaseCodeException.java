package com.xuanluan.mc.sdk.exception;

import lombok.Getter;

@Getter
public abstract class BaseCodeException extends RuntimeException {
    private String code;

    public BaseCodeException(String message) {
        super(message);
    }

    public BaseCodeException(String code, String message) {
        super(message);
        this.code = code;
    }
}
