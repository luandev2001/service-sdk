package com.xuanluan.mc.sdk.utils;


import com.xuanluan.mc.sdk.exception.ServiceException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

/**
 * @author Xuan Luan
 * @createdAt 11/8/2022
 */
public class StringUtils {
    public static boolean hasText(String text) {
        return text != null && !text.trim().isEmpty();
    }

    public static boolean checkSuffixImage(String file) {
        return checkSuffix(file, List.of("png", "jpg", "jpeg"));
    }

    public static boolean checkSuffix(String file, List<String> extensions) {
        return hasText(file) && extensions.stream().anyMatch(file::endsWith);
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    /**
     * prefix= "string" is char from a to z (aaa -> aaz)
     * suffix= "number" from 1 to 99999999
     * item= <string>.<number>
     */
    public static String generateAlphabetDotNoCode(String oldValue) {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        //not have text => item= "a.1"
        if (!StringUtils.hasText(oldValue)) return alphabet[0] + "." + 1;
        String[] item = oldValue.split("\\.");
        //check item has valid
        Integer suffix = toInteger(item[1]);
        if (item.length != 2 || toInteger(item[0]) == null || suffix == null) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "Mã ký tự không hợp lệ, nguyên tắc 'chuỗi.số'");
        }
        int maxSuffix = 999999999;
        if (suffix <= 0 || suffix > maxSuffix) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "Invalid data, 0<data<" + maxSuffix, "Dữ liệu không hợp lệ, 0<data<" + maxSuffix);
        } else if (suffix < maxSuffix) {
            ++suffix;
            return item[0] + "." + suffix;
        } else {
            char[] charactersPrefix = item[0].toCharArray();
            char cLast = charactersPrefix[charactersPrefix.length - 1];
            if (cLast == alphabet[alphabet.length - 1]) {
                //item=<prefix>a.<suffix>
                return new String(charactersPrefix) + alphabet[0] + "." + item[1];
            } else {
                for (int index = 0; index < alphabet.length - 1; index++) {
                    //change char last = char next in alphabet
                    if (cLast == alphabet[index]) {
                        charactersPrefix[charactersPrefix.length - 1] = alphabet[++index];
                        return new String(charactersPrefix) + "." + 1;
                    }
                }
                throw new ServiceException(HttpStatus.BAD_REQUEST, "Invalid generate data", "Khởi tạo dữ liệu không thành công");
            }
        }
    }

    private static Integer toInteger(String text) {
        try {
            return Integer.valueOf(text);
        } catch (Exception e) {
            return null;
        }
    }
}
