package com.xuanluan.mc.service.impl;

import com.xuanluan.mc.domain.entity.Configuration;
import com.xuanluan.mc.domain.model.request.ConfigurationDTO;
import com.xuanluan.mc.service.IConfigurationService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ConfigurationServiceImpl implements IConfigurationService {
    @Override
    public Configuration create(String clientId, ConfigurationDTO dto, String byUser) {
        return null;
    }

    @Override
    public List<Configuration> getList(String clientId) {
        return null;
    }

    @Override
    public List<Configuration> getList(String clientId, Collection<String> orgIds) {
        return null;
    }

    @Override
    public Configuration get(String clientId, String name) {
        return null;
    }

    @Override
    public Configuration update(String id, ConfigurationDTO dto, String byUser) {
        return null;
    }
}
