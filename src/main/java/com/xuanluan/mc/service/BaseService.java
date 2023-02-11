package com.xuanluan.mc.service;

import com.xuanluan.mc.exception.ServiceException;
import com.xuanluan.mc.utils.BaseStringUtils;
import com.xuanluan.mc.utils.NumberUtils;
import org.springframework.http.HttpStatus;

import java.util.*;

public class BaseService {

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    private synchronized static long getValue() {
        final long limit = 10_000_000_000L;
        //10 digits
        long value = System.currentTimeMillis() % limit;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return value;
    }

    private static String formatDigit(long count) {
        if (count < 1000) {
            return "" + count;
        }
        int exp = (int) (Math.log(count) / Math.log(1000));
        if (exp < 4) {
            return String.format("%.1f%c", count / Math.pow(1000, exp), "KMBT".charAt(exp - 1));
        } else {
            return String.format("%.1f%c", count / Math.pow(1000, 3), "KMBT".charAt(3));
        }
    }

    //throw exception
    private static void throwExceptionText() throws ServiceException {
        throw new ServiceException(
                HttpStatus.BAD_REQUEST,
                "Invalid item text!",
                "Mã ký tự không hợp lệ, yêu cầu mã theo nguyên tắc 'chuỗi.số'"
        );
    }

    //check text, request text = <string>.<number>
    private static void validateText(String[] item) throws ServiceException {
        if (item.length != 2 || NumberUtils.isNumeric(item[0]) || !NumberUtils.isNumeric(item[1])) {
            throwExceptionText();
        }
    }

    /**
     * prefix= "string" is char from a to z (aaa -> aaz)
     * suffix= "number" from 1 to 9999
     * item= string+"."+number
     *
     * @param oldValue
     * @return
     */
    public static String generateAlphabetDotNoCode(String oldValue) throws ServiceException {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        //not have text => item= "a.1"
        if (!BaseStringUtils.hasTextAfterTrim(oldValue)) {
            return alphabet[0] + "." + 1;
        }

        String[] item = oldValue.split("\\.");
        //check item has valid
        validateText(item);

        long suffix = NumberUtils.convertTextToNumber(item[1]);

        if (suffix < 0 || suffix > 9999) {
            throw new ServiceException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid data, 0<data<9999",
                    "Dữ liệu không hợp lệ, 0<data<9999"
            );
        } else if (suffix < 9999) {
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
                return null;
            }
        }
    }
}
