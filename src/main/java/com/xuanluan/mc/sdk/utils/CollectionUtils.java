package com.xuanluan.mc.sdk.utils;

import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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

    public static <T> Function<T, List<T>> append(Supplier<Boolean> supplier, List<T> list) {
        return (value) -> {
            if (supplier.get() != null && supplier.get()) list.add(value);
            return list;
        };
    }

    public static <T> T get(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception _e) {
            return null;
        }
    }
}
