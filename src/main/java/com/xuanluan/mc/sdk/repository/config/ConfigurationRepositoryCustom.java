package com.xuanluan.mc.sdk.repository.config;

import com.xuanluan.mc.sdk.domain.entity.Configuration;
import com.xuanluan.mc.sdk.domain.model.filter.ConfigurationFilter;
import com.xuanluan.mc.sdk.domain.model.filter.ResultList;

public interface ConfigurationRepositoryCustom {
    ResultList<Configuration> search(ConfigurationFilter filter);
}
