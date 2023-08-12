package com.xuanluan.mc.domain.enums;

public enum ActivityLogType {
    CREATE("Tạo"),
    UPDATE("Cập Nhật"),
    CANCEL("Hủy Bỏ"),
    COMMENT("Nhận Xét");

    public final String label;

    ActivityLogType(String label) {
        this.label = label;
    }
}
