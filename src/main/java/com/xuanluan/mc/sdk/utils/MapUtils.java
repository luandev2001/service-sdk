package com.xuanluan.mc.sdk.utils;

import java.util.HashMap;

/**
 * @author Xuan Luan
 * @createdAt 4/5/2023
 */
public class MapUtils {
    public static <T> HashMap<String, T> append(String key, T value, HashMap<String, T> oldMap) {
        ExceptionUtils.notBlank("Key", key);
        if (value != null) {
            oldMap.put(key, value);
        }
        return oldMap;
    }
}
