package com.xuanluan.mc.domain.enums;

/**
 * @author Xuan Luan
 * @createdAt 8/11/2022
 */
public enum RoleAccount {
    SUPER_ADMIN("SA", "Quản trị viên cấp cao"),
    ADMIN("AD", "Quản trị viên"),
    EMPLOYEE("EMP", "Nhân viên"),
    CUSTOMER("CUS", "Khách hàng");

    public final String abbreviation;
    public final String label;

    RoleAccount(String abbreviation, String label) {
        this.abbreviation = abbreviation;
        this.label = label;
    }
}
