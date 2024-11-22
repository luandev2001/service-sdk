package com.xuanluan.mc.sdk.utils;

import com.xuanluan.mc.sdk.model.enums.CalendarField;

import java.util.Date;

/**
 * @author Xuan Luan
 * @createdAt 11/1/2022
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static Date getStart(Date date, CalendarField field) {
        return truncate(date, field.getValue());
    }

    public static Date getEnd(Date date, CalendarField field) {
        Date endOfDay = ceiling(date, field.getValue());
        return addMilliseconds(endOfDay, -1);
    }
}
