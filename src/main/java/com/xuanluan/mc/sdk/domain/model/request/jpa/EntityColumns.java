package com.xuanluan.mc.sdk.domain.model.request.jpa;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Map;

public interface EntityColumns<T> {
    Collection<T> getColumns();

    Map<String, Collection<T>> getAssociatedColumns();

    @Getter
    @Setter
    class BaseColumn {
        private String name;
    }
}
