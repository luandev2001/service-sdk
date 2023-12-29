package com.xuanluan.mc.sdk.exception;

public class TenantException extends RuntimeException {
    public TenantException() {
    }

    public TenantException(String message) {
        super(message);
    }

    public TenantException(String message, Throwable cause) {
        super(message, cause);
    }

    public TenantException(Throwable cause) {
        super(cause);
    }
}
