package com.xuanluan.mc.sdk.controller;

import com.xuanluan.mc.sdk.domain.model.WrapperResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

/**
 * @author Xuan Luan
 * @createdAt 3/2/2023
 */
@RequiredArgsConstructor
public class BaseController {
    private final MessageSource messageSource;

    protected <T> WrapperResponse<T> get(T data, String arg) {
        return responseMethod(data, "rest.get", arg);
    }

    protected <T> WrapperResponse<T> create(T data, String arg) {
        return responseMethod(data, "rest.create", arg);
    }

    protected <T> WrapperResponse<T> update(T data, String arg) {
        return responseMethod(data, "rest.update", arg);
    }

    protected <T> WrapperResponse<T> delete(T data, String arg) {
        return responseMethod(data, "rest.delete", arg);
    }

    protected <T> WrapperResponse<T> responseMethod(T data, String messageKey, String arg) {
        String message = messageSource.getMessage(messageKey, new Object[]{arg}, LocaleContextHolder.getLocale());
        return response(data, message);
    }

    protected <T> WrapperResponse<T> response(T data, String message) {
        return WrapperResponse.<T>builder().status(HttpStatus.OK).message(message).data(data).build();
    }
}
