package com.xuanluan.mc.sdk.repository.config;

import com.xuanluan.mc.sdk.domain.entity.Configuration;
import com.xuanluan.mc.sdk.domain.model.filter.ConfigurationFilter;
import com.xuanluan.mc.sdk.domain.model.filter.ResultList;

import java.util.List;

public interface ConfigurationRepositoryCustom {
    List<Configuration> findAll(String clientId);

    List<Configuration> findAll(String clientId, String type);

    Configuration findByName(String clientId, String name, String type);

    ResultList<Configuration> search(String clientId, ConfigurationFilter filter);
}
