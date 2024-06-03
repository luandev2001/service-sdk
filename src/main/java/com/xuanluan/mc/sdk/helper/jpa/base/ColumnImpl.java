package com.xuanluan.mc.sdk.helper.jpa.base;

import com.xuanluan.mc.sdk.domain.model.request.jpa.EntityColumns;
import com.xuanluan.mc.sdk.utils.CollectionUtils;

import javax.persistence.criteria.Path;

public class ColumnImpl<T, C extends EntityColumns.BaseColumn> implements IColumn<T, C> {
    private final Path<T> path;
    private final C column;

    public ColumnImpl(Path<T> path, C column) {
        this.path = path;
        this.column = column;
    }

    @Override
    public String getName() {
        return column.getName();
    }

    @Override
    public boolean isValid() {
        return getPath() != null;
    }

    @Override
    public C getColumn() {
        return column;
    }

    public <Y> Path<Y> getPath() {
        return CollectionUtils.get(() -> path.get(getName()));
    }
}
