package com.xuanluan.mc.sdk.controller;

import com.xuanluan.mc.sdk.domain.model.WrapperResponse;
import com.xuanluan.mc.sdk.exception.MessageException;
import com.xuanluan.mc.sdk.utils.MessageUtils;
import org.springframework.http.HttpStatus;

/**
 * @author Xuan Luan
 * @createdAt 3/2/2023
 */
public class BaseController {
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
        MessageUtils.Message message = MessageUtils.get(messageKey);
        MessageException.MessageObject messageObject = MessageException.convert(arg);
        return response(data, String.format(message.getVn(), messageObject.getVns()).trim(), String.format(message.getEn(), messageObject.getEns()).trim());
    }

    protected <T> WrapperResponse<T> response(T data, String messageVN, String messageEN) {
        return WrapperResponse.<T>builder().status(HttpStatus.OK).message_vn(messageVN).message(messageEN).data(data).build();
    }
}
