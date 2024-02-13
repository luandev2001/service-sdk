package com.xuanluan.mc.sdk.exception;

import lombok.Getter;
@Getter
public class MessageException extends RuntimeException {

    public MessageException(String message) {
        super(message);
    }
}
