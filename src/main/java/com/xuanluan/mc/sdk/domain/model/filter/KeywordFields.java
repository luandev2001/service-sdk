package com.xuanluan.mc.sdk.domain.model.filter;

import java.util.Map;
import java.util.Set;

public interface SearchFields {
    Set<String> against();

    Map<String, Set<String>> associated_against();
}
