package com.xuanluan.mc.sdk.utils;

import com.xuanluan.mc.sdk.exception.MessageException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Objects;

public final class AssertUtils {
    private AssertUtils() {
        throw new AssertionError("No java.util.Objects instances for you!");
    }

    public static void isTrue(boolean expression, String key, HttpStatus status, Object... args) {
        if (!expression) {
            throw MessageException.assign(key, status, args);
        }
    }

    public static void isTrue(boolean expression, String key, Object... args) {
        isTrue(expression, key, HttpStatus.BAD_REQUEST, args);
    }

    public static void notMatch(@Nullable Object value1, @Nullable Object value2, String arg) {
        isTrue(Objects.equals(value1, value2), "error.not_match", arg);
    }

    public static void isTrue(boolean expression, String arg) {
        isTrue(expression, "error.not_correct", HttpStatus.BAD_REQUEST, arg);
    }

    public static void notNull(@Nullable Object value, String key, HttpStatus status, Object... args) {
        if (value == null) {
            throw MessageException.assign(key, status, args);
        }
    }

    public static void notNull(@Nullable Object value, String key, Object... args) {
        notNull(value, key, HttpStatus.BAD_REQUEST, args);
    }

    public static void notNull(@Nullable Object value, String arg) {
        notNull(value, "error.not_null", arg);
    }

    public static void notEmpty(@Nullable Collection<?> values, String key, Object... args) {
        if (CollectionUtils.isEmpty(values)) {
            throw MessageException.assign(key, HttpStatus.BAD_REQUEST, args);
        }
    }

    public static void notEmpty(Collection<?> values, String arg) {
        notEmpty(values, "error.not_empty", arg);
    }

    public static void notFound(@Nullable Object value, String arg1, String arg2) {
        notNull(value, "error.not_found", HttpStatus.NOT_FOUND, arg1, arg2);
    }

    public static void notBlank(@Nullable String value, String arg) {
        isTrue(BaseStringUtils.hasTextAfterTrim(value), "error.not_blank", arg);
    }
}
