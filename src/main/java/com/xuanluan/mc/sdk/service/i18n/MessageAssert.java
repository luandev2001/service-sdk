package com.xuanluan.mc.sdk.service.i18n;

import com.xuanluan.mc.sdk.exception.BadRequestException;
import com.xuanluan.mc.sdk.exception.NotFoundException;
import com.xuanluan.mc.sdk.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Objects;

@RequiredArgsConstructor
public class MessageAssert {
    private final MessageSourceAccessor messageSource;

    public void isTrue(boolean expression, String key, @Nullable Object... args) {
        if (!expression) {
            String message = messageSource.getMessage(key, args);
            throw new BadRequestException(message);
        }
    }

    public void notMatch(@Nullable Object value1, @Nullable Object value2, Object... args) {
        isTrue(Objects.equals(value1, value2), "error.not_match", args);
    }

    public void isTrue(boolean expression, Object... args) {
        isTrue(expression, "error.not_correct", args);
    }

    public void notNull(@Nullable Object value, String key, @Nullable Object... args) {
        if (value == null) {
            String message = messageSource.getMessage(key, args);
            throw new BadRequestException(message);
        }
    }

    public void notNull(@Nullable Object value, Object... args) {
        notNull(value, "error.not_null", args);
    }

    public void notEmpty(@Nullable Collection<?> values, String key, @Nullable Object... args) {
        if (CollectionUtils.isEmpty(values)) {
            String message = messageSource.getMessage(key, args);
            throw new BadRequestException(message);
        }
    }

    public void notEmpty(@Nullable Collection<?> values, Object... args) {
        notEmpty(values, "error.not_empty", args);
    }

    public void notFound(@Nullable Object value, @Nullable Object... args) {
        if (value == null) {
            String message = messageSource.getMessage("error.not_found", args);
            throw new NotFoundException(message);
        }
    }

    public void notBlank(@Nullable String value, Object... args) {
        isTrue(StringUtils.hasText(value), "error.not_blank", args);
    }
}
