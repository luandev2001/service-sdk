package com.xuanluan.mc.domain.enums;

public enum ActivityLogType {
    CREATE("Tạo"),
    UPDATE("Cập Nhật"),
    CANCEL("Hủy Bỏ"),
    COMPLETE("Hoàn Thành"),
    COMMENT("Nhận Xét");

    private final String label;

    ActivityLogType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
