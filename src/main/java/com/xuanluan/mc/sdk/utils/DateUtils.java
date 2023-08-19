package com.xuanluan.mc.sdk.utils;

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
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            return calendar.getTime();
        } else {
            return null;
        }
    }

    public static Date getEndDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (null != date) {
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            return calendar.getTime();
        } else {
            return null;
        }
    }
}
