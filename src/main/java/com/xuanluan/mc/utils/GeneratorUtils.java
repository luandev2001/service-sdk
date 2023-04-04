package com.xuanluan.mc.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author Xuan Luan
 * @createdAt 3/31/2023
 */
public class GeneratorUtils {
    public static String generateRegexRandom() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}";
        return RandomStringUtils.random(20, characters);
    }

    public static String generateUsernameFromEmail(String email) {
        ExceptionUtils.notBlank("email", email);
        String splitEmail = email.split("@gmail")[0];
        return splitEmail.replaceAll("[^0-9a-zA-Z]", "");
    }
}
