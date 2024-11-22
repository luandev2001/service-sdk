package com.xuanluan.mc.sdk.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Calendar;

@Getter
@RequiredArgsConstructor
public enum CalendarField {
    DATE(Calendar.DATE),
    WEEK(Calendar.WEEK_OF_YEAR),
    DAY_OF_WEEK(Calendar.DAY_OF_WEEK),
    MONTH(Calendar.MONTH),
    DAY_OF_MONTH(Calendar.DAY_OF_MONTH),
    YEAR(Calendar.YEAR),
    DAY_OF_YEAR(Calendar.DAY_OF_YEAR);

    private final int value;
}
