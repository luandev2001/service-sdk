package com.xuanluan.mc.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Xuan Luan
 * @createdAt 11/1/2022
 */
public class DateUtils {
    public static Date getStartDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (null != date) {
            calendar.setTime(date);
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(14, 0);
            calendar.set(13, 0);
            return calendar.getTime();
        } else {
            return null;
        }
    }

    public static Date getEndDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (null != date) {
            calendar.setTime(date);
            calendar.set(11, 23);
            calendar.set(12, 59);
            calendar.set(13, 59);
            return calendar.getTime();
        } else {
            return null;
        }
    }
}
