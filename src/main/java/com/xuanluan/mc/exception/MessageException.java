package com.xuanluan.mc.exception;

import com.xuanluan.mc.utils.MessageUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MessageException extends RuntimeException {
    private final String vn;
    private final String en;
    private final HttpStatus status;

    public MessageException(String vn, String en, HttpStatus status) {
        super(en);
        this.vn = vn;
        this.en = en;
        this.status = status;
    }

    public static MessageException assign(String key, HttpStatus status, Object... args) {
        MessageUtils.Message message = MessageUtils.get(key);
        return new MessageException(String.format(message.getVn(), args), String.format(message.getEn(), args), status);
    }

    public static MessageException assign(String key, Object... args) {
        return assign(key, HttpStatus.INTERNAL_SERVER_ERROR, args);
    }
}
