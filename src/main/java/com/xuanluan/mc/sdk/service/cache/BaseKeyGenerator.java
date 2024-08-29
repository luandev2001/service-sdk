package com.xuanluan.mc.sdk.service.cache;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BaseKeyGenerator {
    public String generate(Object... params) {
        String key = "";
        if (params != null && params.length > 0) {
            key = Stream.of(params).map(Object::toString).collect(Collectors.joining(":"));
        }
        return key;
    }
}
