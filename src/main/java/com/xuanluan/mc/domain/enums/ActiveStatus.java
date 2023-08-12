package com.xuanluan.mc.domain.enums;

public enum ActiveStatus {
    active("kích hoạt"),
    delete("xóa"),
    inactive("tạm ẩn");

    public final String label;

    ActiveStatus(String label) {
        this.label = label;
    }
}
