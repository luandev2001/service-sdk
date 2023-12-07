package com.xuanluan.mc.sdk.exception;

import com.xuanluan.mc.sdk.utils.MessageUtils;
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
        MessageObject messageObject = convert(args);
        return new MessageException(String.format(message.getVn(), messageObject.vns).trim(), String.format(message.getEn(), messageObject.ens).trim(), status);
    }

    public static MessageException assign(String key, Object... args) {
        return assign(key, HttpStatus.INTERNAL_SERVER_ERROR, args);
    }

    public static MessageObject convert(Object... args) {
        Object[] vns = null;
        Object[] ens = null;
        if (args != null && args.length > 0) {
            vns = new Object[args.length];
            ens = new Object[args.length];
            for (int index = 0; index < args.length; index++) {
                if (args[index] instanceof String) {
                    String key = (String) args[index];
                    MessageUtils.Message message = MessageUtils.get(key);
                    vns[index] = message.getVn() != null ? message.getVn() : key;
                    ens[index] = message.getEn() != null ? message.getEn() : key;
                } else {
                    vns[index] = args[index];
                    ens[index] = args[index];
                }
            }
        }
        MessageObject messageObject = new MessageObject();
        messageObject.vns = vns;
        messageObject.ens = ens;
        return messageObject;
    }

    @Getter
    public static class MessageObject {
        Object[] vns;
        Object[] ens;
    }
}
