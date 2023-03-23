package com.xuanluan.mc.controller;

import com.xuanluan.mc.domain.model.WrapperResponse;
import org.springframework.http.HttpStatus;

/**
 * @author Xuan Luan
 * @createdAt 3/2/2023
 */
public class BaseController {
    protected <T> WrapperResponse<T> response(String mess, T data) {
        return new WrapperResponse<>(HttpStatus.OK, mess, data);
    }
}
