package com.xuanluan.mc.sdk.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DataType {
    STRING(1), NUMBER(2), MAP(3), LIST(4), SET(5);

    private final int value;
}
