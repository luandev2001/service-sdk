package com.xuanluan.mc.sdk.model.request.configuration;

import com.xuanluan.mc.sdk.model.enums.DataType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateConfiguration extends ConfigurationRequest {
    private String name;
    private String type;
    private boolean isEdit = true;
    private DataType dataType;
}
