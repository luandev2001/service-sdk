package com.xuanluan.mc.sdk.service.impl;

import com.xuanluan.mc.sdk.domain.entity.Configuration;
import com.xuanluan.mc.sdk.domain.model.request.ConfigurationDTO;
import com.xuanluan.mc.sdk.repository.config.ConfigurationRepository;
import com.xuanluan.mc.sdk.service.converter.ConfigurationConverter;
import com.xuanluan.mc.sdk.utils.AssertUtils;
import com.xuanluan.mc.sdk.service.IConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@CacheConfig(cacheNames = {"configurations"})
@RequiredArgsConstructor
@Service
public class ConfigurationServiceImpl implements IConfigurationService {
    private final ConfigurationRepository configurationRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Configuration create(String clientId, ConfigurationDTO dto, String byUser) {
        List<Configuration> configurations = create(clientId, List.of(dto), byUser);
        AssertUtils.notEmpty(configurations, "error.create_failed", "Configuration");
        return configurations.get(0);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Configuration> create(final String clientId, List<ConfigurationDTO> dtos, String byUser) {
        AssertUtils.notEmpty(dtos, "request");
        List<Configuration> configurations = dtos.stream()
                .map(dto -> {
                    final String nameConvert = ConfigurationConverter.replaceName(dto.getName());
                    Configuration configuration = configurationRepository.findByName(clientId, nameConvert);
                    if (configuration == null) {
                        configuration = ConfigurationConverter.toConfiguration(new Configuration(), dto);
                        configuration.setClientId(clientId);
                        configuration.setCreatedBy(byUser);
                        AssertUtils.notBlank(configuration.getClientId(), "client");
                        AssertUtils.isTrue(nameConvert.equals(configuration.getName()), "configuration.name not equal nameConvert");
                        AssertUtils.notBlank(configuration.getName(), "name");
                        AssertUtils.notBlank(configuration.getType(), "type");
                    }
                    return configuration;
                })
                .collect(Collectors.toList());
        return !configurations.isEmpty() ? (List<Configuration>) configurationRepository.saveAll(configurations) : null;
    }

    @Cacheable
    @Override
    public List<Configuration> getList(String clientId) {
        return configurationRepository.findAll(clientId);
    }

    @Cacheable(key = "clientId.concat('.'+name)")
    @Override
    public Configuration get(final String clientId, String name) {
        AssertUtils.notBlank(clientId, "client");
        AssertUtils.notBlank(name, "name");
        Configuration configuration = configurationRepository.findByName(clientId, ConfigurationConverter.replaceName(name));
        AssertUtils.notFound(configuration, "Configuration", "clientId: " + clientId + ", name: " + name);
        return configuration;
    }

    @CachePut(key = "clientId.concat('.'+dto.getName())")
    @Override
    public Configuration update(final String clientId, ConfigurationDTO dto, String byUser) {
        AssertUtils.notNull(dto, "request");
        AssertUtils.notBlank(dto.getName(), "name");
        final String nameConvert = dto.getName();
        Configuration configuration = configurationRepository.findByName(clientId, nameConvert);
        AssertUtils.notFound(configuration, "Configuration", "clientId: " + clientId + ", name: " + dto.getName());
        configuration = ConfigurationConverter.toConfiguration(configuration, dto);
        AssertUtils.isTrue(nameConvert.equals(configuration.getName()), "configuration.name not equal nameConvert");
        configuration.setUpdatedBy(byUser);
        return configurationRepository.save(configuration);
    }
}
