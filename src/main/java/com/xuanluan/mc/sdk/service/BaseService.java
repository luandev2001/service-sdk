package com.xuanluan.mc.sdk.service;

import com.xuanluan.mc.sdk.utils.AssertUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

public abstract class BaseService {
    protected abstract CacheManager getCacheManager();

    public abstract String getNameCache();

    private Cache checkCache() {
        AssertUtils.notBlank(getNameCache(), "name_cache");
        Cache cache = getCacheManager().getCache(getNameCache());
        AssertUtils.notFound(cache, "cache", "name: " + getNameCache());
        return cache;
    }

    public void putCache(String key, Object value) {
        checkCache().put(key, value);
    }

    public void getCache(String key, Object value) {
        checkCache().put(key, value);
    }

    public void clearAllCache() {
        checkCache().clear();
    }

    public void clearCache(String key) {
        checkCache().evict(key);
    }
}
