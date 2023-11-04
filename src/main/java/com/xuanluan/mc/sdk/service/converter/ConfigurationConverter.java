package com.xuanluan.mc.sdk.service.converter;

import com.xuanluan.mc.sdk.domain.entity.Configuration;
import com.xuanluan.mc.sdk.domain.model.request.ConfigurationDTO;
import com.xuanluan.mc.sdk.utils.AssertUtils;

public class ConfigurationConverter {
    public static Configuration toConfiguration(Configuration configuration, ConfigurationDTO dto) {
        AssertUtils.notNull(dto, "ConfigurationDTO");
        configuration.setValue(dto.getValue());
        configuration.setType(dto.getType());
        configuration.setName(replaceName(dto.getName()));
        configuration.setEdit(dto.isEdit());
        return configuration;
    }

    public static String replaceName(String name) {
        AssertUtils.notNull(name, "name");
        return name.trim().replaceAll("[^a-zA-Z0-9-]", "-").toLowerCase();
    }
}
