package com.xuanluan.mc.exception;

import com.xuanluan.mc.domain.model.WrapperResponse;
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

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(ServiceException.class)
    public WrapperResponse handleServiceException(ServiceException e) {

        return WrapperResponse.builder()
                .status(e.getStatus())
                .data(e.getData())
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public WrapperResponse handleInputDataException(MethodArgumentNotValidException e) {
        return processFieldErrors(e);
    }

    private WrapperResponse processFieldErrors(MethodArgumentNotValidException exception) {

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
    public WrapperResponse handleException(Exception e) {

        return WrapperResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .data("Đã xảy ra lỗi: " + e.getMessage())
                .message(e.getMessage())
                .build();
    }

//    private enum ErrorCode {
//        SERVICE_PROCESS_ERROR(8888, "Service Process Error", "Lỗi Xử Lý Từ Các Service");
//
//        public final int status;
//        public final String label;
//        public final String desc;
//
//        ErrorCode(int status, String label, String desc) {
//            this.status = status;
//            this.label = label;
//            this.desc = desc;
//        }
//    }
}