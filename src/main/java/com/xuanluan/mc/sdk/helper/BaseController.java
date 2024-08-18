package com.xuanluan.mc.sdk.helper;

import com.xuanluan.mc.sdk.model.WrapperResponse;
import com.xuanluan.mc.sdk.service.i18n.MessageLocale;
import lombok.RequiredArgsConstructor;

/**
 * @author Xuan Luan
 * @createdAt 3/2/2023
 */
@RequiredArgsConstructor
public class BaseController {
    private final MessageLocale messageLocale;

    protected <T> WrapperResponse<T> get(T data, String arg) {
        return response(data, "rest.get", arg);
    }

    protected <T> WrapperResponse<T> create(T data, String arg) {
        return response(data, "rest.create", arg);
    }

    protected <T> WrapperResponse<T> update(T data, String arg) {
        return response(data, "rest.update", arg);
    }

    protected <T> WrapperResponse<T> delete(T data, String arg) {
        return response(data, "rest.delete", arg);
    }

    protected <T> WrapperResponse<T> response(T data, String messageKey, Object... args) {
        String message = messageLocale.get(messageKey, args);
        return WrapperResponse.<T>builder().message(message).data(data).build();
    }
}
