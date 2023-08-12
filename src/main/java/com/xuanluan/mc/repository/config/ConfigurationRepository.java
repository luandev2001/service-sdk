package com.xuanluan.mc.repository.config;

import com.xuanluan.mc.domain.entity.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends CrudRepository<Configuration, String>, ConfigurationRepositoryCustom {
}
