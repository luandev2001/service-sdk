package com.xuanluan.mc.sdk.service.converter;

import org.springframework.util.Assert;

public class ConfigurationConverter {
    public static String replaceName(String name) {
        Assert.notNull(name, "name not null");
        return name.trim().replaceAll("[^a-zA-Z0-9-]", "_").toLowerCase();
    }
}
