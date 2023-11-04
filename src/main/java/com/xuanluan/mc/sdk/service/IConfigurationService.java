package com.xuanluan.mc.sdk.service;

import com.xuanluan.mc.sdk.domain.entity.Configuration;
import com.xuanluan.mc.sdk.domain.model.request.ConfigurationDTO;

import java.util.List;
import java.util.Map;

public interface IConfigurationService {
    Configuration create(String clientId, ConfigurationDTO dto, String byUser);

    List<Configuration> create(String clientId, List<ConfigurationDTO> dtos, String byUser);

    Configuration get(String clientId, String name, String type);

    Map<String, Object> getValue(String clientId, String name, String type);

    Configuration update(String clientId, ConfigurationDTO dto, String byUser);
}
