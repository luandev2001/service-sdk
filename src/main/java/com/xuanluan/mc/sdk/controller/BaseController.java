package com.xuanluan.mc.sdk.controller;

import com.xuanluan.mc.sdk.domain.model.WrapperResponse;
import org.springframework.http.HttpStatus;

/**
 * @author Xuan Luan
 * @createdAt 3/2/2023
 */
public class BaseController {

    protected <T> WrapperResponse<T> response_error(String mess, T data) {
        return response(mess, data, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected <T> WrapperResponse<T> response(String mess, T data) {
        return response(mess, data, HttpStatus.OK);
    }

    protected <T> WrapperResponse<T> response(String mess, T data, HttpStatus status) {
        return WrapperResponse.<T>builder().status(status).message(mess).data(data).build();
    }
}
