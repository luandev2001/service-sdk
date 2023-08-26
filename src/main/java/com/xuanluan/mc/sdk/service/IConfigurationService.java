package com.xuanluan.mc.sdk.service;

import com.xuanluan.mc.sdk.domain.entity.Configuration;
import com.xuanluan.mc.sdk.domain.model.request.ConfigurationDTO;

import java.util.List;

public interface IConfigurationService {
    Configuration create(String clientId, ConfigurationDTO dto, String byUser);

    List<Configuration> create(String clientId, List<ConfigurationDTO> dtos, String byUser);

    List<Configuration> getList(String clientId);

    List<Configuration> getList(String clientId, String type);

    Configuration get(String clientId, String name, String type);

    Configuration update(String clientId, ConfigurationDTO dto, String byUser);
}
