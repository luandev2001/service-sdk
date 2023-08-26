package com.xuanluan.mc.sdk.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActionMethod {
    GET(1), CREATE(2), UPDATE(3), DELETE(4);

    private final int value;
}
