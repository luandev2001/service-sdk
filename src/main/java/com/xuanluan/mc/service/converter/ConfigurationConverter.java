package com.xuanluan.mc.service.converter;

import com.xuanluan.mc.domain.entity.Configuration;
import com.xuanluan.mc.domain.model.request.ConfigurationDTO;
import org.springframework.util.Assert;

public class ConfigurationConverter {
    public static Configuration toConfiguration(Configuration configuration, ConfigurationDTO dto) {
        Assert.notNull(dto, "ConfigurationDTO must be not null");
        if (configuration == null) configuration = new Configuration();
        configuration.setValue(dto.getValue());
        configuration.setType(dto.getType());
        configuration.setName(dto.getName());
        return configuration;
    }
}
