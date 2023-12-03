package com.xuanluan.mc.sdk.domain.model.filter;

import com.xuanluan.mc.sdk.domain.enums.DataType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class ConfigurationFilter extends BaseFilter {
    private Set<String> types;
    private Set<DataType> dataTypes;
}
