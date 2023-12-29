package com.xuanluan.mc.sdk.exception;

import com.xuanluan.mc.sdk.domain.model.WrapperResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Xuan Luan
 * @createdAt 8/29/2022
 */
@RequiredArgsConstructor
@Slf4j
public class ResponseExceptionHandler {
    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(TenantException.class)
    public WrapperResponse<Object> handleTenantException(TenantException e) {
        return WrapperResponse.builder()
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
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
                .status(e.getStatus())
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public WrapperResponse<Object> handleInputDataException(MethodArgumentNotValidException e) {
        return processFieldErrors(e);
    }

    private WrapperResponse<Object> processFieldErrors(MethodArgumentNotValidException exception) {
        Map<String, String> errorDetail = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String messageError = error.getDefaultMessage();
            errorDetail.put(fieldName, messageError);
        });
        return WrapperResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Dữ liệu nhập vào không hợp lệ")
                .data(errorDetail)
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