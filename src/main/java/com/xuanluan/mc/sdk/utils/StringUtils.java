package com.xuanluan.mc.sdk.utils;


import com.xuanluan.mc.sdk.exception.BadRequestException;
import org.springframework.util.Assert;

import java.util.UUID;

/**
 * @author Xuan Luan
 * @createdAt 11/8/2022
 */
public class StringUtils extends org.springframework.util.StringUtils {
    public static String replaceSpecial(String name, String replaceText) {
        Assert.notNull(name, "name not null");
        return name.trim().replaceAll("[^a-zA-Z0-9-]", replaceText).toLowerCase();
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    /**
     * prefix= "string" is char from a to z (aaa -> aaz)
     * suffix= "number" from 1 to maxSuffix
     * item= <string>.<number>
     */
    public static String generateAlphabetDotNoCode(String oldValue, int maxSuffix) {
        final int start = 'a';
        final int end = 'z';

        if (!StringUtils.hasText(oldValue)) return (char) start + "." + 1;

        String[] item = oldValue.split("\\.");
        Assert.isTrue(item.length == 2, "Invalid format for oldValue");

        int suffix = Integer.parseInt(item[1]);
        Assert.isTrue(suffix >= 0 && suffix <= maxSuffix, "Invalid format for oldValue");
        if (suffix < maxSuffix) return item[0] + "." + (++suffix);

        char[] charactersPrefix = item[0].toCharArray();
        char firstChar = charactersPrefix[0];
        if (firstChar == end) return (char) start + new String(charactersPrefix) + "." + item[1];

        if (firstChar >= start && firstChar < end) {
            charactersPrefix[0] = (char) (firstChar + 1);
            return new String(charactersPrefix) + "." + 1;
        } else {
            throw new BadRequestException("Invalid generate alphabet dot number");
        }
    }
}
