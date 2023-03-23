package com.xuanluan.mc.utils;

import com.xuanluan.mc.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * @author Xuan Luan
 * @createdAt 3/19/2023
 */
public class ExceptionUtils {
    /**
     * not found data of name field
     *
     * @param name  nameField
     * @param value valueField
     */
    public static void notFoundData(@Nullable String name, @Nullable String value, @Nullable Object result, @Nullable String messVN, @Nullable String messEN) {
        if (result == null) {
            throw new ServiceException(
                    HttpStatus.NOT_FOUND,
                    "Không tìm thấy dữ liệu" + messVN + name + value,
                    "Not found data" + messEN + name + value);
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
        Assert.notNull(fieldName, "Tên trường không được để trống!");
        if (input instanceof String && !BaseStringUtils.hasTextAfterTrim((String) input)) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, (fieldName + " must not be blank").trim(), (fieldName + " phải chứa ít nhất 1 ký tự").trim());
        }
        notNull(fieldName, input);
    }

    public static void notNull(String fieldName, @Nullable Object input) {
        Assert.notNull(fieldName, "Tên trường không được để trống!");
        if (input == null) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, (fieldName + " must not be null").trim(), (fieldName + " không được để trống").trim());
        }
    }

    public static void notNegative(String fieldName, double value) {
        Assert.notNull(fieldName, "Tên trường không được để trống!");
        if (value < 0) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, (fieldName + " must not be negative").trim(), (fieldName + " không được âm").trim());
        }
    }

}
