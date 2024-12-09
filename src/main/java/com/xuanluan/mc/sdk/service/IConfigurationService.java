package com.xuanluan.mc.sdk.service;

import com.xuanluan.mc.sdk.model.entity.Configuration;
import com.xuanluan.mc.sdk.model.request.configuration.CreateConfiguration;
import com.xuanluan.mc.sdk.model.request.configuration.UpdateConfiguration;

import java.util.List;

public interface IConfigurationService {
    Configuration create(CreateConfiguration dto);

    List<Configuration> create(List<CreateConfiguration> dtos);

    Configuration get(String name, String type);

    Object getValue(String name, String type);

    Configuration update(UpdateConfiguration dto);
}
