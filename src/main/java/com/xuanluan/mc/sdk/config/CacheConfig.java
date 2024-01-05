package com.xuanluan.mc.sdk.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.xuanluan.mc.sdk.service.constant.BaseConstant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {
    @Value("${cache.primary.expire_time:7}")
    private int expireTime;
    @Value("${cache.primary.period:DAYS}")
    private String period;
    @Value("${cache.primary.maxsize:500}")
    private int maxSize;

    @Bean
    @Primary
    public CacheManager cacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .expireAfterWrite(expireTime, TimeUnit.valueOf(period.toUpperCase()))
                        .maximumSize(maxSize)
        );
        return caffeineCacheManager;
    }

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setPropertyCondition(BaseConstant.Mapper.skipEntityFields);
        return modelMapper;
    }
}
