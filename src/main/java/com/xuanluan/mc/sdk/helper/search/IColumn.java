package com.xuanluan.mc.sdk.helper.search;

public interface IColumnConfiguration {
    String getTableName();

    String getColumnName();

    default String getFullName() {
        return getTableName() + "." + getColumnName();
    }

    default String to_sql() {
        return String.format("coalesce(%s::text, '')", getFullName());
    }
}
