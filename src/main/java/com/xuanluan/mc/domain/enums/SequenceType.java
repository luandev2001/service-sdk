package com.xuanluan.mc.domain.enums;

/**
 * Sequence will auto increase
 *
 * @author Xuan Luan
 * @createdAt 12/17/2022
 */
public enum SequenceType {
    NUMBER(1, "Số"),
    ALPHABET_DOT_NO(2, "Chữ, kí tự '.' và số ");

    public final int value;
    public final String label;

    SequenceType(int value, String label) {
        this.value = value;
        this.label = label;
    }
}
