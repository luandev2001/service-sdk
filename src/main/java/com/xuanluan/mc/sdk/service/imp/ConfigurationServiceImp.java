package com.xuanluan.mc.sdk.service.imp;

import com.xuanluan.mc.sdk.model.entity.Configuration;
import com.xuanluan.mc.sdk.model.enums.DataType;
import com.xuanluan.mc.sdk.model.request.configuration.ConfigurationRequest;
import com.xuanluan.mc.sdk.model.request.configuration.CreateConfiguration;
import com.xuanluan.mc.sdk.model.request.configuration.UpdateConfiguration;
import com.xuanluan.mc.sdk.repository.config.ConfigurationRepository;
import com.xuanluan.mc.sdk.service.constant.BaseConstant;
import com.xuanluan.mc.sdk.service.i18n.MessageAssert;
import com.xuanluan.mc.sdk.service.tenant.TenantIdentifierResolver;
import com.xuanluan.mc.sdk.service.IConfigurationService;
import com.xuanluan.mc.sdk.utils.NumberUtils;
import com.xuanluan.mc.sdk.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@ConditionalOnProperty(name = "sdk.configuration.enabled", havingValue = "true")
public class ConfigurationServiceImp implements IConfigurationService {
    private final ConfigurationRepository configurationRepository;
    private final ModelMapper modelMapper;
    private final CacheManager cacheManager;
    private final TenantIdentifierResolver tenantIdentifierResolver;
    private final MessageAssert messageAssert;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Configuration create(CreateConfiguration dto) {
        List<Configuration> configurations = create(List.of(dto));
        messageAssert.notEmpty(configurations, "error.create.failed", "Configuration");
        return configurations.get(0);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Configuration> create(List<CreateConfiguration> dtos) {
        messageAssert.notEmpty(dtos, "request");
        List<Configuration> configurations = dtos.stream()
                .map(dto -> {
                    final String nameConvert = StringUtils.replaceSpecial(dto.getName(), "_");
                    dto.setName(nameConvert);
                    validateDataType(dto.getDataType(), dto);

                    Configuration configuration = null;
                    if (!configurationRepository.existsByNameAndType(nameConvert, dto.getType())) {
                        configuration = modelMapper.map(dto, Configuration.class);
                        messageAssert.notBlank(configuration.getName(), "name");
                        messageAssert.notBlank(configuration.getType(), "type");
                        messageAssert.notNull(configuration.getDataType(), "data_type");
                    }
                    return configuration;
                })
                .filter(Objects::nonNull).collect(Collectors.toList());
        return !configurations.isEmpty() ? configurationRepository.saveAll(configurations) : null;
    }

    @Override
    public Configuration get(String name, String type) {
        messageAssert.notBlank(name, "name");
        messageAssert.notBlank(type, "type");

        Configuration configuration = configurationRepository.findByNameAndType(name, type);
        messageAssert.notFound(configuration, "configuration", "name: " + name);

        return configuration;
    }

    @Override
    public Object getValue(String name, String type) {
        String key = getKeyCache(name, type);
        Object value = getCache().get(key, Object.class);
        if (value == null) {
            value = get(name, type).getValue();
            getCache().put(key, value);
        }
        return value;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Configuration update(UpdateConfiguration dto) {
        messageAssert.notNull(dto, "request");
        messageAssert.notBlank(dto.getName(), "name");
        messageAssert.notBlank(dto.getType(), "type");

        final String nameConvert = StringUtils.replaceSpecial(dto.getName(), "_");
        Configuration configuration = configurationRepository.findByNameAndType(nameConvert, dto.getType());
        messageAssert.notFound(configuration, "configuration", "name: " + dto.getName());
        messageAssert.isTrue(configuration.isEdit(), "error.not_modify", "configuration");
        validateDataType(configuration.getDataType(), dto);

        configuration.setValue(dto.getValue());
        configuration = configurationRepository.save(configuration);
        //clear cache
        getCache().evict(getKeyCache(configuration.getName(), configuration.getType()));
        return configuration;
    }

    private void validateDataType(DataType dataType, ConfigurationRequest request) {
        messageAssert.notNull(dataType, "data_type");

        Object value = request.getValue();
        if (value != null) {
            switch (dataType) {
                case STRING:
                    messageAssert.isTrue(value instanceof String, "error.data_type", dataType);
                    break;
                case MAP:
                    messageAssert.isTrue(value instanceof Map, "error.data_type", dataType);
                    break;
                case SET:
                    messageAssert.isTrue(value instanceof Set, "error.data_type", dataType);
                    break;
                case LIST:
                    messageAssert.isTrue(value instanceof List, "error.data_type", dataType);
                    break;
                case NUMBER:
                    Optional<Double> toDouble = NumberUtils.convertToDouble(String.valueOf(value));
                    messageAssert.isTrue(toDouble.isPresent(), "error.data_type", dataType);
                    request.setValue(toDouble.orElse(0.0));
                    break;
            }
        }
    }

    private String getKeyCache(String name, String type) {
        return String.join(":", tenantIdentifierResolver.resolveCurrentTenantIdentifier(), name, type);
    }

    private Cache getCache() {
        return cacheManager.getCache(BaseConstant.CacheName.CONFIGURATION);
    }
}
