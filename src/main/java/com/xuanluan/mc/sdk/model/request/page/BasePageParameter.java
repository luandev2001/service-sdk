package com.xuanluan.mc.sdk.model.request.page;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public abstract class BasePageParameter {
    private int size;
    private int page;
    private String keyword;
    private List<SortParameter> sorts;
    private List<FilterParameter> filters;

    public abstract Set<String> keywordParams();
}
