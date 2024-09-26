package com.xuanluan.mc.sdk.service.builder;

import lombok.Builder;
import lombok.Singular;

import java.util.HashMap;
import java.util.Map;

@Builder
public class MapBuilder<Key, Value> {
    @Singular("data")
    private Map<Key, Value> map;

    public Map<Key, Value> getHashMap() {
        return new HashMap<>(map);
    }
}