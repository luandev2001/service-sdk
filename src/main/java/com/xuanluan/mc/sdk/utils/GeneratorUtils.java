package com.xuanluan.mc.sdk.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

/**
 * @author Xuan Luan
 * @createdAt 3/31/2023
 */
public class GeneratorUtils {

    public static String getRandomCode4Digits() {
        return generateCodeDigits(4);
    }

    public static String generateCodeDigits(int length) {
        AssertUtils.isTrue(length > 0 && length < 10, "0 < length < 10");
        char[] oneDigits = new char[length];
        char[] nineDigits = new char[length];
        oneDigits[0] = '1';
        nineDigits[0] = '9';
        for (int i = 1; i < length; i++) {
            oneDigits[i] = '0';
            nineDigits[i] = '0';
        }
        //      format int -> string
        int min = Integer.parseInt(new String(oneDigits));
        int max = Integer.parseInt(new String(nineDigits));
        return "" + (new Random().nextInt(max) + min);
    }

    public static String generateRegexRandom() {
        return generateRegexRandom(20);
    }

    public static String generateRegexRandom(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}";
        return RandomStringUtils.random(length, characters);
    }

    public static String generateUsernameFromEmail(String email) {
        AssertUtils.notBlank(email, "email");
        String splitEmail = email.split("@gmail")[0];
        return splitEmail.replaceAll("[^0-9a-zA-Z]", "");
    }
}
