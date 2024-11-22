package com.xuanluan.mc.sdk.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.web.client.RestTemplate;

/**
 * @author Xuan Luan
 * @createdAt 3/31/2023
 */
public class GeneratorUtils {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    public static final RestTemplate restTemplate = new RestTemplate();

    public static String randomDigits(int length) {
        return new RandomStringGenerator.Builder().withinRange('0', '9').build().generate(length);
    }

    public static String random(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}";
        return RandomStringUtils.random(length, characters);
    }
}
