package com.xuanluan.mc.sdk.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConfirmationStatus {
    approving("Chờ phê duyệt"),
    processing("Đang hoạt động"),
    closing("Tạm ngưng");

    private final String label;
}
