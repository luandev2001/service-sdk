package com.xuanluan.mc.sdk.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

/**
 * @author Xuan Luan
 * @createdAt 3/31/2023
 */
public class GeneratorUtils {

    public static String getRandomCode4Digits() {
//        [0,9999], format int -> string
        return String.format("%4d", new Random().nextInt(9000) + 1000);

    }

    public static String generateRegexRandom() {
        return generateRegexRandom(20);
    }

    public static String generateRegexRandom(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}";
        return RandomStringUtils.random(length, characters);
    }

    public static String generateUsernameFromEmail(String email) {
        ExceptionUtils.notBlank("email", email);
        String splitEmail = email.split("@gmail")[0];
        return splitEmail.replaceAll("[^0-9a-zA-Z]", "");
    }
}
