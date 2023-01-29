package com.xuanluan.mc.domain.enums;

public enum ActiveStatus {
    active("kích hoạt"),
    delete("xóa"),
    inactive("tạm ẩn");

    private final String label;

    ActiveStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
