package com.xuanluan.mc.sdk.service.impl;

import com.xuanluan.mc.sdk.domain.entity.Configuration;
import com.xuanluan.mc.sdk.domain.enums.DataType;
import com.xuanluan.mc.sdk.domain.model.request.CreateConfiguration;
import com.xuanluan.mc.sdk.domain.model.request.UpdateConfiguration;
import com.xuanluan.mc.sdk.repository.config.ConfigurationRepository;
import com.xuanluan.mc.sdk.service.builder.CacheBuilder;
import com.xuanluan.mc.sdk.service.constant.BaseConstant;
import com.xuanluan.mc.sdk.service.converter.ConfigurationConverter;
import com.xuanluan.mc.sdk.service.tenant.TenantIdentifierResolver;
import com.xuanluan.mc.sdk.utils.AssertUtils;
import com.xuanluan.mc.sdk.service.IConfigurationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ConfigurationServiceImpl implements IConfigurationService {
    private final ConfigurationRepository configurationRepository;
    private final ModelMapper modelMapper;
    private final CacheManager cacheManager;
    private final TenantIdentifierResolver tenantIdentifierResolver;

    private CacheBuilder<Configuration> configurationCache;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Configuration create(CreateConfiguration dto, String byUser) {
        List<Configuration> configurations = create(List.of(dto), byUser);
        AssertUtils.notEmpty(configurations, "error.create_failed", "Configuration");
        return configurations.get(0);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Configuration> create(List<CreateConfiguration> dtos, String byUser) {
        AssertUtils.notEmpty(dtos, "request");
        List<Configuration> configurations = dtos.stream()
                .map(dto -> {
                    final String nameConvert = ConfigurationConverter.replaceName(dto.getName());
                    dto.setName(nameConvert);
                    validateDataType(dto.getDataType(), dto.getValue());

                    Configuration configuration = configurationRepository.findByName(nameConvert, dto.getType());
                    if (configuration == null) {
                        configuration = modelMapper.map(dto, Configuration.class);
                        configuration.setCreatedBy(byUser);
                        AssertUtils.isTrue(nameConvert.equals(configuration.getName()), "configuration.name not equal nameConvert");
                        AssertUtils.notBlank(configuration.getName(), "name");
                        AssertUtils.notBlank(configuration.getType(), "type");
                    }
                    return configuration;
                })
                .collect(Collectors.toList());
        return !configurations.isEmpty() ? (List<Configuration>) configurationRepository.saveAll(configurations) : null;
    }

    @Override
    public Configuration get(String name, String type) {
        AssertUtils.notBlank(name, "name");
        AssertUtils.notBlank(type, "type");

        String nameConverted = ConfigurationConverter.replaceName(name);
        Configuration configuration = getCache().get(getKeyCache(nameConverted, type));
        if (configuration != null) return configuration;

        configuration = configurationRepository.findByName(nameConverted, type);
        AssertUtils.notFound(configuration, "configuration", "name: " + name);

        getCache().put(getKeyCache(nameConverted, type), configuration);
        return configuration;
    }

    @Override
    public Object getValue(String name, String type) {
        return get(name, type).getValue();
    }

    @Override
    public Configuration update(UpdateConfiguration dto, String byUser) {
        AssertUtils.notNull(dto, "request");
        AssertUtils.notBlank(dto.getName(), "name");
        AssertUtils.notBlank(dto.getType(), "type");

        final String nameConvert = ConfigurationConverter.replaceName(dto.getName());
        Configuration configuration = configurationRepository.findByName(nameConvert, dto.getType());
        AssertUtils.notFound(configuration, "configuration", "name: " + dto.getName());
        AssertUtils.isTrue(configuration.isEdit(), "error.not_modify", "configuration");
        validateDataType(configuration.getDataType(), dto.getValue());

        configuration.setValue(dto.getValue());
        configuration.setUpdatedBy(byUser);
        configuration.setUpdatedAt(new Date());
        configuration = configurationRepository.save(configuration);
        //update cache
        getCache().put(getKeyCache(configuration.getName(), configuration.getType()), configuration);
        return configuration;
    }

    @Override
    public CacheBuilder<Configuration> getCache() {
        if (configurationCache == null) {
            configurationCache = new CacheBuilder<>(cacheManager, BaseConstant.CacheName.configuration, Configuration.class);
        }
        return configurationCache;
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

    private String getKeyCache(String name, String type) {
        return tenantIdentifierResolver.resolveCurrentTenantIdentifier() + "_" + name + "_" + type;
    }
}
