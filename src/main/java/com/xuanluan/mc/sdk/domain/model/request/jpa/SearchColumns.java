package com.xuanluan.mc.sdk.domain.model.request.jpa;

public interface SearchColumns extends EntityColumns<SearchColumns.Column> {
    String getKeyword();

    class Column extends BaseColumn {
    }
}
