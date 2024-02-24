package com.xuanluan.mc.sdk.repository.config;

import com.xuanluan.mc.sdk.domain.entity.Configuration;
import com.xuanluan.mc.sdk.domain.model.filter.ConfigurationFilter;
import org.springframework.data.domain.Page;

public interface ConfigurationRepositoryCustom {
    Page<Configuration> search(ConfigurationFilter filter);
}
