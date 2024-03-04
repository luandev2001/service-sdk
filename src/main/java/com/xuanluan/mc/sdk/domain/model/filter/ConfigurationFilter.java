package com.xuanluan.mc.sdk.domain.model.filter;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ConfigurationFilter extends BaseFilter {

    @Override
    public Set<String> keywordParams() {
        return Set.of("name", "type");
    }
}
