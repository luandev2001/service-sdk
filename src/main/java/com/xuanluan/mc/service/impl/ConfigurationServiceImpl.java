package com.xuanluan.mc.service.impl;

import com.xuanluan.mc.domain.entity.Configuration;
import com.xuanluan.mc.domain.model.request.ConfigurationDTO;
import com.xuanluan.mc.repository.config.ConfigurationRepository;
import com.xuanluan.mc.service.IConfigurationService;
import com.xuanluan.mc.service.converter.ConfigurationConverter;
import com.xuanluan.mc.utils.AssertUtils;
import com.xuanluan.mc.utils.BaseStringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ConfigurationServiceImpl implements IConfigurationService {
    private final ConfigurationRepository configurationRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Configuration create(String clientId, ConfigurationDTO dto, String byUser) {
        return create(clientId, List.of(dto), byUser).get(0);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Configuration> create(String clientId, List<ConfigurationDTO> dtos, String byUser) {
        AssertUtils.notEmpty(dtos);
        List<Configuration> configurations = dtos.stream()
                .filter(dto -> get(clientId, dto.getName()) != null)
                .map(dto -> {
                    Configuration configuration = ConfigurationConverter.toConfiguration(new Configuration(), dto);
                    configuration.setClientId(clientId);
                    configuration.setCreatedBy(byUser);

                    AssertUtils.isTrue(BaseStringUtils.hasTextAfterTrim(configuration.getClientId()), "error.invalid_client");
                    AssertUtils.isTrue(BaseStringUtils.hasTextAfterTrim(configuration.getCreatedBy()), "error.by_user");
                    AssertUtils.isTrue(BaseStringUtils.hasTextAfterTrim(configuration.getName()), "error.not_blank");
                    AssertUtils.isTrue(BaseStringUtils.hasTextAfterTrim(configuration.getType()), "error.not_blank");
                    return configuration;
                })
                .collect(Collectors.toList());
        return (List<Configuration>) configurationRepository.saveAll(configurations);
    }

    @Override
    public List<Configuration> getList(String clientId) {
        return configurationRepository.findAll(clientId);
    }

    @Override
    public Configuration get(String clientId, String name) {
        return configurationRepository.findByName(clientId, name);
    }

    @Override
    public Configuration update(String id, ConfigurationDTO dto, String byUser) {
        return null;
    }
}
