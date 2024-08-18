package com.xuanluan.mc.sdk.service.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;

public class MessageLocale extends MessageSourceAccessor {
    public MessageLocale(MessageSource messageSource) {
        super(messageSource);
    }

    public String get(String code, Object... args) {
        return getMessage(code, args, code);
    }
}
