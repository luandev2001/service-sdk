package com.xuanluan.mc.sdk.repository.config;

import com.xuanluan.mc.sdk.domain.entity.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends CrudRepository<Configuration, String>, ConfigurationRepositoryCustom {
    Configuration findByNameAndType(String name, String type);

}
