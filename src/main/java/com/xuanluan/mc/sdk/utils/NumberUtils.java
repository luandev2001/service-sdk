package com.xuanluan.mc.sdk.utils;

import java.util.Optional;

public class NumberUtils extends org.springframework.util.NumberUtils {
    public static boolean isNumeric(Object value) {
        return convertToDouble(value).isPresent();
    }

    public static double toNumeric(Object value) {
        return convertToDouble(value).orElse(0.0);
    }

    private static Optional<Double> convertToDouble(Object value) {
        try {
            return Optional.of(Double.valueOf(value.toString()));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
