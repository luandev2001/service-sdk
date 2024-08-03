package com.xuanluan.mc.sdk.exception;

public class TenantException extends BaseCodeException {

    public TenantException(String message) {
        super(message);
    }

    public TenantException(String code, String message) {
        super(code, message);
    }
}
