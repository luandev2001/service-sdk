package com.xuanluan.mc.sdk.service.builder;

import com.xuanluan.mc.sdk.utils.AssertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

@RequiredArgsConstructor
public class CacheBuilder<T> {

    private final CacheManager cacheManager;

    private final String name;

    private final Class<T> type;

    private Cache checkCache() {
        AssertUtils.notBlank(name, "name_cache");
        Cache cache = cacheManager.getCache(name);
        AssertUtils.notFound(cache, "cache", "name: " + name);
        return cache;
    }

    public void put(String key, T value) {
        checkCache().put(key, value);
    }

    public T get(String key) {
        return checkCache().get(key, type);
    }

    public void clearAll() {
        checkCache().clear();
    }

    public void clear(String key) {
        checkCache().evict(key);
    }
}
