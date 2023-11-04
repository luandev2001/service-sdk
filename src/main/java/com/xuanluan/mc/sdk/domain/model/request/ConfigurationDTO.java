package com.xuanluan.mc.sdk.domain.model.request;

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
    private boolean isEdit;
}
