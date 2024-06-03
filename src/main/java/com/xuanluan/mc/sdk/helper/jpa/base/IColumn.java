package com.xuanluan.mc.sdk.helper.jpa.base;

import com.xuanluan.mc.sdk.domain.model.request.jpa.EntityColumns;

import javax.persistence.criteria.Path;

public interface IColumn<T, C> extends IBase {
    C getColumn();

    <Y> Path<Y> getPath();
}
