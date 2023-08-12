package com.xuanluan.mc.domain.model.request;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Getter
@Builder
public class ConfigurationDTO {
    private final Set<String> orgIds;
    private final String name;
    private final Map<String, Object> value;
    private final String type;
}
