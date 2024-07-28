package com.xuanluan.mc.sdk.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Sequence will auto increase
 *
 * @author Xuan Luan
 * @createdAt 12/17/2022
 */
@Getter
@RequiredArgsConstructor
public enum SequenceType {
    NUMBER("Số"),
    ALPHABET_DOT_NO("Chữ, kí tự '.' và số ");

    private final String label;
}
