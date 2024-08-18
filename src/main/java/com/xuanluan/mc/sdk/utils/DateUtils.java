package com.xuanluan.mc.sdk.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Xuan Luan
 * @createdAt 11/1/2022
 */
public class DateUtils {
    public static Date getStartDay(Date date) {
        date = date != null ? date : new Date(0);
        Calendar calendar = new Calendar.Builder().setTimeOfDay(0, 0, 0, 0).build();
        calendar.setTime(date);
        return calendar.getTime();
    }

    public static Date getEndDay(Date date) {
        date = date != null ? date : new Date();
        Calendar calendar = new Calendar.Builder().setTimeOfDay(23, 59, 59, 999).build();
        calendar.setTime(date);
        return calendar.getTime();
    }
}
