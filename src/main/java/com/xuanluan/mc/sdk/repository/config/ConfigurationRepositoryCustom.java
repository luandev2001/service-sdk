package com.xuanluan.mc.sdk.repository.config;

import com.xuanluan.mc.sdk.domain.model.filter.BaseFilter;
import org.springframework.data.domain.Page;

public interface ConfigurationRepositoryCustom {
    <X extends BaseFilter> Page<?> search(X filter);
}
