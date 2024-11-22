package com.xuanluan.mc.sdk.utils;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class LambdaUtils {
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
