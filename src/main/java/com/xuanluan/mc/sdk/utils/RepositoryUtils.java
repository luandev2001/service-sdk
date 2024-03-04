package com.xuanluan.mc.sdk.utils;

import org.springframework.data.domain.Sort;

public class RepositoryUtils {
    public static Sort.Direction convertDirection(String direction) {
        return Sort.Direction.ASC.name().equals(direction.toUpperCase()) ? Sort.Direction.ASC : Sort.Direction.DESC;
    }
}
