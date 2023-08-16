package com.xuanluan.mc.exception;

import com.xuanluan.mc.domain.model.WrapperResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ResponseExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ResponseExceptionHandler.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceException.class)
    public WrapperResponse<Object> handleServiceException(ServiceException e) {
        return WrapperResponse.builder()
                .status(e.getStatus())
                .message_vn((String) e.getData())
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MessageException.class)
    public WrapperResponse<Object> handleMessageException(MessageException e) {
        return WrapperResponse.builder()
                .status(e.getStatus())
                .message_vn(e.getVn())
                .message(e.getEn())
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
                .message("Invalid input data!")
                .data(errorDetail)
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public WrapperResponse<Object> handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return WrapperResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .data("Đã xảy ra lỗi: " + e.getMessage())
                .message(e.getMessage())
                .build();
    }
}