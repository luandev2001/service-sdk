package com.xuanluan.mc.sdk.exception;

import lombok.Getter;

@Getter
public class MessageSourceException extends BaseCodeException {
    private String code;
    private final Object[] args;

    public MessageSourceException(String message, Object... args) {
        this(null, message, args);
    }

    public MessageSourceException(String code, String message, Object... args) {
        super(code, message);
        this.args = args;
    }
}
