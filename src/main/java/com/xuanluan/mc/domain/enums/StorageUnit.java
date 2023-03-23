package com.xuanluan.mc.domain.enums;

/**
 * 1KB= 1024bytes
 * 1MB = 1024 * 1024
 * 1GB = 1024 * 1024 * 1024
 * 1TB = 1024 * 1024 * 1024 * 1024
 *
 * @author Xuan Luan
 * @createdAt 3/14/2023
 */
public enum StorageUnit {
    TB(1099511627776L), GB(1073741824), MB(1048576);

    public final long value;

    StorageUnit(long value) {
        this.value = value;
    }
}