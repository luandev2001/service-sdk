package com.xuanluan.mc.service;

import com.xuanluan.mc.domain.entity.Configuration;
import com.xuanluan.mc.domain.model.request.ConfigurationDTO;

import java.util.Collection;
import java.util.List;

public interface IConfigurationService {
    Configuration create(String clientId, ConfigurationDTO dto, String byUser);

    List<Configuration> getList(String clientId);

    List<Configuration> getList(String clientId, Collection<String> orgIds);

    Configuration get(String clientId, String name);

    Configuration update(String id, ConfigurationDTO dto, String byUser);
}
