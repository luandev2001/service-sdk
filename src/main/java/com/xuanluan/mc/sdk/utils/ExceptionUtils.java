package com.xuanluan.mc.sdk.utils;

import com.xuanluan.mc.sdk.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * @author Xuan Luan
 * @createdAt 3/19/2023
 */
public class ExceptionUtils {
    public static void notFoundData(@Nullable String name, @Nullable String value, @Nullable Object result, @Nullable String messVN, @Nullable String messEN) {
        if (result == null) {
            throw new ServiceException(HttpStatus.NOT_FOUND, "Không tìm thấy dữ liệu" + messVN + name + value, "Not found data" + messEN + name + value);
        }
    }

    public static void notFoundData(String name, String value, @Nullable Object result) {
        notFoundData(name + ": ", value, result, ", ", ", ");
    }

    public static void notFoundData(@Nullable Object result, String messVN, String messEN) {
        notFoundData("", "", result, " " + messVN, " " + messEN);
    }

    public static void notFoundData(@Nullable Object result) {
        notFoundData("", "", result, "", "");
    }

    public static void invalidInput(String fieldName, @Nullable Object input) throws ServiceException {
        if (input instanceof String) {
            notBlank(fieldName, (String) input);
        } else if (input instanceof Collection<?>) {
            notEmpty(fieldName, (Collection<?>) input);
        } else {
            notNull(fieldName, input);
        }
    }

    public static void notNull(String fieldName, @Nullable Object input) {
        Assert.notNull(fieldName, "Tên trường không được để trống!");
        if (input == null) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, (fieldName + " must not be null").trim(), (fieldName + " không được để trống").trim());
        }
    }

    public static void notBlank(String fieldName, @Nullable String input) {
        notNull(fieldName, input);
        if (!BaseStringUtils.hasTextAfterTrim(input)) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, (fieldName + " must not be blank").trim(), (fieldName + " phải chứa ít nhất 1 ký tự").trim());
        }
    }

    public static void notEmpty(String fieldName, @Nullable Collection<?> collection) {
        notNull(fieldName, collection);
        if (CollectionUtils.isEmpty(collection)) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, (fieldName + " must not be empty").trim(), (fieldName + " không được để rỗng").trim());
        }
    }

    public static void notNegative(String fieldName, double value) {
        Assert.notNull(fieldName, "Tên trường không được để trống!");
        if (value < 0) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, (fieldName + " must not be negative").trim(), (fieldName + " không được âm").trim());
        }
    }

    public static void isTrue(boolean expression, HttpStatus status, String messVN, String messEN) {
        if (!expression) {
            throw new ServiceException(status, messEN, messVN);
        }
    }

    public static void isTrue(boolean expression, String messVN, String messEN) {
        isTrue(expression, HttpStatus.BAD_REQUEST, messVN, messEN);
    }
}
