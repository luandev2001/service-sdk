package com.xuanluan.mc.sdk.domain.model.filter;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public abstract class BaseFilter {
    private int size;
    private int page;
    private String keyword;
    private Map<String, String> sorts = new HashMap<>();
    private Map<String, Object> filters = new HashMap<>();

    public abstract Set<String> keywordParams();
}
