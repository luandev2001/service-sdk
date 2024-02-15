package com.xuanluan.mc.sdk.service.locale;

import com.xuanluan.mc.sdk.exception.MessageException;
import com.xuanluan.mc.sdk.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class MessageAssert {
    private final MessageSourceAccessor messageSourceAccessor;

    public void isTrue(boolean expression, String key, @Nullable Object... args) {
        if (!expression) {
            String message = messageSourceAccessor.getMessage(key, args);
            throw new MessageException(message);
        }
    }

    public void notMatch(@Nullable Object value1, @Nullable Object value2, String arg) {
        isTrue(Objects.equals(value1, value2), "error.not_match", arg);
    }

    public void isTrue(boolean expression, String arg) {
        isTrue(expression, "error.not_correct", arg);
    }

    public void notNull(@Nullable Object value, String key, @Nullable Object... args) {
        if (value == null) {
            String message = messageSourceAccessor.getMessage(key, args);
            throw new MessageException(message);
        }
    }

    public void notNull(@Nullable Object value, String arg) {
        notNull(value, "error.not_null", arg);
    }

    public void notEmpty(@Nullable Collection<?> values, String key, @Nullable Object... args) {
        if (CollectionUtils.isEmpty(values)) {
            String message = messageSourceAccessor.getMessage(key, args);
            throw new MessageException(message);
        }
    }

    public void notEmpty(@Nullable Collection<?> values, String arg) {
        notEmpty(values, "error.not_empty", arg);
    }

    public void notFound(@Nullable Object value, String arg1, String arg2) {
        notNull(value, "error.not_found", arg1, arg2);
    }

    public void notBlank(@Nullable String value, String arg) {
        isTrue(StringUtils.hasText(value), "error.not_blank", arg);
    }
}
