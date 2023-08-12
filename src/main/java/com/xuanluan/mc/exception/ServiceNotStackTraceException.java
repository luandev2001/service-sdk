package com.xuanluan.mc.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * @author Xuan Luan
 * @createdAt 11/11/2022
 */
@Getter
@Setter
public class ServiceNotStackTraceException extends Exception {
    private HttpStatus errorCode;
    private String errorMessage;
    private Object errorDetail;

    public ServiceNotStackTraceException(HttpStatus errorCode, String errorMessage, Object errorDetail) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDetail = errorDetail;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
