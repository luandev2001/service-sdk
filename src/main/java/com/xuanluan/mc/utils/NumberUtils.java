package com.xuanluan.mc.utils;

/**
 * @author Xuan Luan
 * @createdAt 12/11/2022
 */
public class NumberUtils {
    public static boolean isNumeric(String string) {
        if (!BaseStringUtils.hasTextAfterTrim(string)) {
            return false;
        }
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
