package com.xuanluan.mc.sdk.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MessageSourceException extends RuntimeException {
    private final HttpStatus status;
    private final Object[] args;

    public MessageSourceException(String message, Object[] args) {
        super(message);
        status = HttpStatus.EXPECTATION_FAILED;
        this.args = args;
    }

    public MessageSourceException(String message, HttpStatus status, Object[] args) {
        super(message);
        this.status = status;
        this.args = args;
    }
}
