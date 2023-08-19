package com.xuanluan.mc.sdk.domain.model.filter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Builder
public class ConfigurationFilter extends BaseFilter {
    private Collection<String> orgIds;
    private Collection<String> types;
}
