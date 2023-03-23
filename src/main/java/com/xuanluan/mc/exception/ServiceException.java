package com.xuanluan.mc.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceException extends RuntimeException {
    private final HttpStatus status;
    private final String message;
    private final Object data;

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = message;
        this.data = message;
    }

    public ServiceException(HttpStatus status, String message, Object data, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ServiceException(HttpStatus status, String message, Object data) {
        super(message);
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
