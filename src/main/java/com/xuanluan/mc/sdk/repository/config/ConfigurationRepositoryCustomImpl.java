package com.xuanluan.mc.sdk.repository.config;

import com.xuanluan.mc.sdk.domain.entity.Configuration;
import com.xuanluan.mc.sdk.domain.model.filter.ConfigurationFilter;
import com.xuanluan.mc.sdk.repository.BaseRepository;
import org.springframework.data.domain.Page;

import javax.persistence.EntityManager;

public class ConfigurationRepositoryCustomImpl extends BaseRepository<Configuration> implements ConfigurationRepositoryCustom {

    protected ConfigurationRepositoryCustomImpl(EntityManager entityManager) {
        super(entityManager, Configuration.class);
    }

    @Override
    public Page<Configuration> search(ConfigurationFilter filter) {
        refresh();
        return getPage(null, filter);
    }
}
