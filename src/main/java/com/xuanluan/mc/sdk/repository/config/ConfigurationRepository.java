package com.xuanluan.mc.sdk.repository.config;

import com.xuanluan.mc.sdk.model.entity.Configuration;
import com.xuanluan.mc.sdk.repository.JpaMultipleRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "sdk.configuration.enabled", havingValue = "true")
public interface ConfigurationRepository extends JpaMultipleRepository<Configuration, String> {
    Configuration findByNameAndType(String name, String type);

    boolean existsByNameAndType(String name, String type);
}
