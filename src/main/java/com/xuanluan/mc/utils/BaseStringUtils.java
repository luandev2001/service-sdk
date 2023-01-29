package com.xuanluan.mc.utils;

/**
 * @author Xuan Luan
 * @createdAt 11/8/2022
 */
public class BaseStringUtils {
    public static boolean hasTextAfterTrim(String text) {
        return text != null && text.trim().length() > 0;
    }

    public static boolean checkSuffixImage(String url) {
        return hasTextAfterTrim(url)
                && (url.endsWith(".png") || url.endsWith(".jpg") || url.endsWith(".jpeg"));
    }
}
