package com.xuanluan.mc.sdk.repository.config;

import com.xuanluan.mc.sdk.domain.entity.Configuration;
import com.xuanluan.mc.sdk.repository.JpaSpecificationRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaSpecificationRepository<Configuration, String>, ConfigurationRepositoryCustom {
    Configuration findByNameAndType(String name, String type);
}
