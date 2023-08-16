package com.xuanluan.mc.repository.config;

import com.xuanluan.mc.domain.entity.Configuration;
import com.xuanluan.mc.domain.model.filter.ConfigurationFilter;
import com.xuanluan.mc.domain.model.filter.ResultList;

import java.util.List;

public interface ConfigurationRepositoryCustom {
    List<Configuration> findAll(String clientId);

    Configuration findByName(String clientId, String name);

    ResultList<Configuration> search(String clientId, ConfigurationFilter filter);
}
