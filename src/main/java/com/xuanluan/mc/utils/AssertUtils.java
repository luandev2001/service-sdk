package com.xuanluan.mc.utils;

import com.xuanluan.mc.exception.MessageException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

public class AssertUtils {

    public static void isTrue(boolean expression, String key, HttpStatus status) {
        if (!expression) {
            throw MessageException.assign(key, status);
        }
    }

    public static void isTrue(boolean expression, String key) {
        isTrue(expression, key, HttpStatus.BAD_REQUEST);
    }

    public static void isTrue(Object value) {
        notNull(value, "error.not_correct");
    }

    public static void notNull(@Nullable Object value, String key, HttpStatus status) {
        if (value == null) {
            throw MessageException.assign(key, status);
        }
    }

    public static void notNull(@Nullable Object value, String key) {
        notNull(value, key, HttpStatus.BAD_REQUEST);
    }

    public static void notNull(Object value) {
        notNull(value, "error.not_null");
    }

    public static void notEmpty(@Nullable Collection<?> values, String key, HttpStatus status) {
        if (CollectionUtils.isEmpty(values)) {
            throw MessageException.assign(key, status);
        }
    }

    public static void notEmpty(@Nullable Collection<?> values, String key) {
        notEmpty(values, key, HttpStatus.BAD_REQUEST);
    }

    public static void notEmpty(Collection<?> values) {
        notEmpty(values, "error.not_empty");
    }
}
