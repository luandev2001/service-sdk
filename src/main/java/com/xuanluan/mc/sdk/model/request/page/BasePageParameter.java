package com.xuanluan.mc.sdk.model.request.page;

import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasePageParameter {
    private int size;
    private int page;
    private String keyword;
    private List<SortParameter> sorts;
    private List<FilterParameter> filters;

    public Set<String> getKeywordParams() {
        return null;
    }
}
