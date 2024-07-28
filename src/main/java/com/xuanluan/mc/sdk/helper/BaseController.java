package com.xuanluan.mc.sdk.helper;

import com.xuanluan.mc.sdk.domain.model.WrapperResponse;
import com.xuanluan.mc.sdk.service.locale.MessageLocale;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

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

    protected <T> WrapperResponse<T> response(T data, String messageKey, String... args) {
        String message = messageLocale.getMessage(messageKey, args);
        return WrapperResponse.<T>builder().status(HttpStatus.OK).message(message).data(data).build();
    }
}
