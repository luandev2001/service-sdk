package com.xuanluan.mc.utils;

import com.xuanluan.mc.exception.ServiceException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.util.*;

public class MessageUtils {
    public static final Set<String> keys = new HashSet<>();
    private static final Map<String, String> messageVNs = new HashMap<>();
    private static final Map<String, String> messageENs = new HashMap<>();

    protected void put(String key, Message data) {
        Assert.notNull(data, "data must be not null");
        Message message = get(key);
        if (!BaseStringUtils.hasTextAfterTrim(message.vn)) {
            put(key, data.vn, MessageType.VIET_NAM);
        }
        if (!BaseStringUtils.hasTextAfterTrim(message.en)) {
            put(key, data.en, MessageType.ENGLISH);
        }
    }

    protected void put(String key, String message, MessageType type) {
        if (BaseStringUtils.hasTextAfterTrim(message)) {
            getType(type).putIfAbsent(key, message);
        }
        keys.add(key);
    }

    public static Message get(String key) {
        Assert.isTrue(BaseStringUtils.hasTextAfterTrim(key), "key must not be blank");
        return Message.builder().
                vn(get(key, MessageType.VIET_NAM))
                .en(get(key, MessageType.ENGLISH))
                .build();
    }

    public static List<Message> getList(Set<String> keys) {
        List<Message> messages = new LinkedList<>();
        if (keys != null && !keys.isEmpty()) {
            for (String key : keys) messages.add(get(key));
        }
        return messages;
    }

    public static List<Message> getAll() {
        return getList(keys);
    }

    public static List<String> getList(MessageType type) {
        return new ArrayList<>(getType(type).values());
    }

    public static String get(String key, MessageType type) {
        return getType(type).get(key);
    }

    private static Map<String, String> getType(MessageType type) {
        Assert.notNull(type, "type must be not null");
        switch (type) {
            case VIET_NAM:
                return messageVNs;
            case ENGLISH:
                return messageENs;
            default:
                throw new ServiceException("Not found language: " + type);
        }
    }

    @Getter
    @Setter
    @Builder
    @ToString
    public static class Message {
        private String vn;
        private String en;
    }

    public enum MessageType {
        VIET_NAM, ENGLISH
    }
}
