package com.xuanluan.mc.sdk.exception;

public class UnsupportedException extends BaseCodeException {
    public UnsupportedException() {

    }

    public UnsupportedException(String message) {
        super(message);
    }

    public UnsupportedException(String code, String message) {
        super(code, message);
    }
}
