package com.xuanluan.mc.sdk.exception;

import com.xuanluan.mc.sdk.domain.model.WrapperResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private final MessageSource messageSource;

    /**
     * message of exception is code to get message
     */
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(MessageException.class)
    public WrapperResponse<Object> handleMessageSourceException(MessageSourceException e) {
        return WrapperResponse.builder()
                .status(e.getStatus())
                .message(messageSource.getMessage(e.getMessage(), e.getArgs(), LocaleContextHolder.getLocale()))
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
        String message = messageSource.getMessage("jpa.error.converter", new Object[]{e.getFrom(), e.getTo()}, LocaleContextHolder.getLocale());
        return WrapperResponse.builder()
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .message(message)
                .build();
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ServiceException.class)
    public WrapperResponse<Object> handleServiceException(ServiceException e) {
        return WrapperResponse.builder()
                .status(e.getStatus())
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