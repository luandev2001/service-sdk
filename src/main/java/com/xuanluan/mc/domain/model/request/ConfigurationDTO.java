package com.xuanluan.mc.domain.model.request;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class ConfigurationDTO {
    private final String name;
    private final Map<String, Object> value;
    private final String type;
}
