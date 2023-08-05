package com.xuanluan.mc.controller;

import com.xuanluan.mc.domain.model.WrapperResponse;
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
        return new WrapperResponse<>(status, mess, data, status.value());
    }
}
