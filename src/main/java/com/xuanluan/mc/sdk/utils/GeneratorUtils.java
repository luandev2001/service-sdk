package com.xuanluan.mc.sdk.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * @author Xuan Luan
 * @createdAt 3/31/2023
 */
public class GeneratorUtils {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    public static final RestTemplate restTemplate = new RestTemplate();

    public static String randomDigits(int length) {
        Assert.isTrue(length > 0 && length < 10, "0 < length < 10");
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

    public static String random(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}";
        return RandomStringUtils.random(length, characters);
    }
}
