package com.xuanluan.mc.sdk.model.request.configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateConfiguration extends ConfigurationRequest {
    private String name;
    private String type;
    private Object value;
}
