package com.xuanluan.mc.sdk.service.builder;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.util.Assert;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class CacheBuilder<T> {

    private final CacheManager cacheManager;

    private final String name;

    private final Class<T> type;

    private Cache checkCache() {
        Assert.notNull(name, "name cache must not null");
        Cache cache = cacheManager.getCache(name);
        Assert.notNull(name, "cache must not null");
        return cache;
    }

    public void put(String key, T value) {
        checkCache().put(key, value);
    }

    public T get(String key) {
        return checkCache().get(key, type);
    }

    public T putIfAbsent(String key, Supplier<T> supplier) {
        T value = get(key);
        if (value == null) {
            value = supplier.get();
            put(key, value);
        }
        return value;
    }

    public void clearAll() {
        checkCache().clear();
    }

    public void clear(String key) {
        checkCache().evict(key);
    }
}
