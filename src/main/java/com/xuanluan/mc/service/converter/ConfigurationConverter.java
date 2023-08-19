package com.xuanluan.mc.service.converter;

import com.xuanluan.mc.domain.entity.Configuration;
import com.xuanluan.mc.domain.model.request.ConfigurationDTO;
import com.xuanluan.mc.utils.AssertUtils;

public class ConfigurationConverter {
    public static Configuration toConfiguration(Configuration configuration, ConfigurationDTO dto) {
        AssertUtils.notNull(dto, "ConfigurationDTO");
        if (configuration == null) configuration = new Configuration();
        configuration.setValue(dto.getValue());
        configuration.setType(dto.getType());
        configuration.setName(replaceName(dto.getName()));
        return configuration;
    }

    public static String replaceName(String name) {
        return name.trim().replaceAll("[^a-zA-Z0-9-]", "-").toLowerCase();
    }
}
