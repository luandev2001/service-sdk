package com.xuanluan.mc.sdk.config;

import com.xuanluan.mc.sdk.domain.model.WrapperResponse;
import com.xuanluan.mc.sdk.exception.*;
import com.xuanluan.mc.sdk.service.i18n.MessageLocale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Xuan Luan
 * @createdAt 8/29/2022
 */
@RequiredArgsConstructor
@Slf4j
public class ResponseExceptionHandler {
    protected final MessageLocale messageLocale;

    /**
     * message of exception is code to get message
     */
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(MessageSourceException.class)
    public WrapperResponse<Object> handleMessageSourceException(MessageSourceException e) {
        return WrapperResponse.builder()
                .status(e.getStatus())
                .message(messageLocale.get(e.getMessage(), e.getArgs()))
                .build();
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(TenantException.class)
    public WrapperResponse<Object> handleTenantException(TenantException e) {
        return WrapperResponse.builder()
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(JpaConverterException.class)
    public WrapperResponse<Object> handleJpaConverterException(JpaConverterException e) {
        String message = messageLocale.get("jpa.error.converter", new Object[]{e.getFrom(), e.getTo()});
        return WrapperResponse.builder()
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .message(message)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public WrapperResponse<Object> handleBadRequestException(BadRequestException e) {
        return WrapperResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MessageException.class)
    public WrapperResponse<Object> handleMessageException(MessageException e) {
        return WrapperResponse.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public WrapperResponse<Object> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return WrapperResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Đã xảy ra lỗi hệ thống")
                .build();
    }
}