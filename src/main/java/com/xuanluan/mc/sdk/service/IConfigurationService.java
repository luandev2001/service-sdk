package com.xuanluan.mc.sdk.service;

import com.xuanluan.mc.sdk.domain.entity.Configuration;
import com.xuanluan.mc.sdk.domain.model.request.CreateConfiguration;
import com.xuanluan.mc.sdk.domain.model.request.UpdateConfiguration;

import java.util.List;

public interface IConfigurationService {
    Configuration create(CreateConfiguration dto);

    List<Configuration> create(List<CreateConfiguration> dtos);

    Configuration get(String name, String type);

    Object getValue(String name, String type);

    Configuration update(UpdateConfiguration dto);
}
