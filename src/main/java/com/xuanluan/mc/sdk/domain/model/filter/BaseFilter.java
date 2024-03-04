package com.xuanluan.mc.sdk.domain.model.filter;

import com.xuanluan.mc.sdk.service.constant.BaseConstant;
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

    public int getSize() {
        if (this.size <= 0) this.size = BaseConstant.Repository.maxResult;
        return this.size;
    }

    public abstract Set<String> keywordParams();
}
