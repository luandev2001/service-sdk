package com.xuanluan.mc.sdk.service;

import com.xuanluan.mc.sdk.domain.entity.Configuration;
import com.xuanluan.mc.sdk.domain.model.request.CreateConfiguration;
import com.xuanluan.mc.sdk.domain.model.request.UpdateConfiguration;

import java.util.List;

public interface IConfigurationService {
    Configuration create(String clientId, CreateConfiguration dto, String byUser);

    List<Configuration> create(String clientId, List<CreateConfiguration> dtos, String byUser);

    Configuration get(String clientId, String name, String type);

    Object getValue(String clientId, String name, String type);

    Configuration update(String clientId, UpdateConfiguration dto, String byUser);
}
