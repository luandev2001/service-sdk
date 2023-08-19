package com.xuanluan.mc.domain.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class ConfigurationDTO {
    private String name;
    private Map<String, Object> value;
    private String type;
}
