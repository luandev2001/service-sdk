package com.xuanluan.mc.sdk.service.impl;

import com.xuanluan.mc.sdk.domain.entity.Configuration;
import com.xuanluan.mc.sdk.domain.enums.DataType;
import com.xuanluan.mc.sdk.domain.model.request.CreateConfiguration;
import com.xuanluan.mc.sdk.domain.model.request.UpdateConfiguration;
import com.xuanluan.mc.sdk.repository.config.ConfigurationRepository;
import com.xuanluan.mc.sdk.service.converter.ConfigurationConverter;
import com.xuanluan.mc.sdk.utils.AssertUtils;
import com.xuanluan.mc.sdk.service.IConfigurationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@CacheConfig(cacheNames = {"configurations"})
@RequiredArgsConstructor
@Service
public class ConfigurationServiceImpl implements IConfigurationService {
    private final ConfigurationRepository configurationRepository;
    private final ModelMapper modelMapper;

    @CachePut(key = "#clientId.concat('.'+#dto.name.trim().replaceAll(\"[^a-zA-Z0-9-]\", \"-\").toLowerCase()).concat('.'+#dto.type)")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Configuration create(String clientId, CreateConfiguration dto, String byUser) {
        List<Configuration> configurations = create(clientId, List.of(dto), byUser);
        AssertUtils.notEmpty(configurations, "error.create_failed", "Configuration");
        return configurations.get(0);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Configuration> create(final String clientId, List<CreateConfiguration> dtos, String byUser) {
        AssertUtils.notEmpty(dtos, "request");
        List<Configuration> configurations = dtos.stream()
                .map(dto -> {
                    final String nameConvert = ConfigurationConverter.replaceName(dto.getName());
                    dto.setName(nameConvert);
                    validateDataType(dto.getDataType(), dto.getValue());

                    Configuration configuration = configurationRepository.findByName(clientId, nameConvert, dto.getType());
                    if (configuration == null) {
                        configuration = modelMapper.map(dto, Configuration.class);
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

    @Cacheable(key = "#clientId.concat('.'+#name.trim().replaceAll(\"[^a-zA-Z0-9-]\", \"-\").toLowerCase()).concat('.'+#type)")
    @Override
    public Configuration get(final String clientId, String name, String type) {
        AssertUtils.notBlank(clientId, "client");
        AssertUtils.notBlank(name, "name");
        AssertUtils.notBlank(type, "type");
        Configuration configuration = configurationRepository.findByName(clientId, ConfigurationConverter.replaceName(name), type);
        AssertUtils.notFound(configuration, "Configuration", "clientId: " + clientId + ", name: " + name);
        return configuration;
    }

    @Cacheable(key = "'get_value/'+#clientId.concat('.'+#name.trim().replaceAll(\"[^a-zA-Z0-9-]\", \"-\").toLowerCase()).concat('.'+#type)")
    @Override
    public Object getValue(String clientId, String name, String type) {
        return get(clientId, name, type).getValue();
    }

    @CachePut(key = "#clientId.concat('.'+#dto.name.trim().replaceAll(\"[^a-zA-Z0-9-]\", \"-\").toLowerCase()).concat('.'+#dto.type)")
    @CacheEvict(key = "'get_value/'+#clientId.concat('.'+#dto.name.trim().replaceAll(\"[^a-zA-Z0-9-]\", \"-\").toLowerCase()).concat('.'+#dto.type)")
    @Override
    public Configuration update(final String clientId, UpdateConfiguration dto, String byUser) {
        AssertUtils.notNull(dto, "request");
        AssertUtils.notBlank(dto.getName(), "name");
        AssertUtils.notBlank(dto.getType(), "type");

        final String nameConvert = ConfigurationConverter.replaceName(dto.getName());
        Configuration configuration = configurationRepository.findByName(clientId, nameConvert, dto.getType());
        AssertUtils.notFound(configuration, "configuration", "clientId: " + clientId + ", name: " + dto.getName());
        AssertUtils.isTrue(configuration.isEdit(), "error.not_modify", "configuration");
        validateDataType(configuration.getDataType(), dto.getValue());

        configuration.setValue(dto.getValue());
        configuration.setUpdatedBy(byUser);
        configuration.setUpdatedAt(new Date());
        return configurationRepository.save(configuration);
    }

    private void validateDataType(DataType dataType, Object value) {
        AssertUtils.notNull(dataType, "data_type");
        if (value != null) {
            switch (dataType) {
                case STRING:
                    AssertUtils.isTrue(value instanceof String, "error.data_type", dataType);
                    break;
                case MAP:
                    AssertUtils.isTrue(value instanceof Map, "error.data_type", dataType);
                    break;
                case SET:
                    AssertUtils.isTrue(value instanceof Set, "error.data_type", dataType);
                    break;
                case LIST:
                    AssertUtils.isTrue(value instanceof List, "error.data_type", dataType);
                    break;
                case NUMBER:
                    AssertUtils.isTrue(value instanceof Double, "error.data_type", dataType);
                    break;
            }
        }
    }
}
