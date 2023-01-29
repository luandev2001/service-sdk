package com.xuanluan.mc.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Xuan Luan
 * @createdAt 11/11/2022
 */
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

    public HttpStatus getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(HttpStatus errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(Object errorDetail) {
        this.errorDetail = errorDetail;
    }
}
