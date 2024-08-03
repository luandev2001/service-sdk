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

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public WrapperResponse<Object> handleForbiddenException(ForbiddenException e) {
        return WrapperResponse.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public WrapperResponse<Object> handleUnauthorizedException(UnauthorizedException e) {
        return response(e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public WrapperResponse<Object> handleNotFoundException(NotFoundException e) {
        return response(e);
    }

    /**
     * message of exception is code to get message
     */
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(MessageSourceException.class)
    public WrapperResponse<Object> handleMessageSourceException(MessageSourceException e) {
        return WrapperResponse.builder()
                .code(e.getCode())
                .message(messageLocale.get(e.getMessage(), e.getArgs()))
                .build();
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(TenantException.class)
    public WrapperResponse<Object> handleTenantException(TenantException e) {
        return response(e);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(JpaConverterException.class)
    public WrapperResponse<Object> handleJpaConverterException(JpaConverterException e) {
        String message = messageLocale.get("jpa.error.converter", e.getFrom(), e.getTo());
        return WrapperResponse.builder()
                .message(message)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public WrapperResponse<Object> handleBadRequestException(BadRequestException e) {
        return response(e);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UnprocessableException.class)
    public WrapperResponse<Object> handleUnprocessableException(UnprocessableException e) {
        return response(e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public WrapperResponse<Object> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return WrapperResponse.builder()
                .message(messageLocale.get("error.internal_server"))
                .build();
    }

    private WrapperResponse<Object> response(BaseCodeException e) {
        return WrapperResponse.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build();
    }
}