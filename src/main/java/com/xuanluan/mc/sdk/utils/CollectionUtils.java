package com.xuanluan.mc.sdk.utils;

import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Xuan Luan
 * @createdAt 4/5/2023
 */
public class CollectionUtils extends org.springframework.util.CollectionUtils {
    public static <T> Map<String, T> append(String key, T value, Map<String, T> oldMap) {
        Assert.notNull(key, "key must not null");
        if (value != null) {
            oldMap.put(key, value);
        }
        return oldMap;
    }
}
