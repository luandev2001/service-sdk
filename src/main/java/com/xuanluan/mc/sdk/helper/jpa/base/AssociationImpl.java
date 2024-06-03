package com.xuanluan.mc.sdk.helper.jpa.base;

import com.xuanluan.mc.sdk.domain.model.request.jpa.EntityColumns;
import com.xuanluan.mc.sdk.utils.CollectionUtils;
import lombok.Getter;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.stream.Collectors;

public class AssociationImpl<T, X, C extends EntityColumns.BaseColumn> implements IAssociation<T, X, C> {
    private final Root<T> root;
    private final String name;
    private final Collection<C> columns;
    @Getter
    private final JoinType joinType;

    public AssociationImpl(Root<T> root, String name, Collection<C> columns, JoinType joinType) {
        this.root = root;
        this.name = name;
        this.columns = columns;
        this.joinType = joinType;
    }

    public AssociationImpl build(Root<T> root, String name, Collection<C> columns) {
        return new AssociationImpl(root, name, columns, JoinType.INNER);
    }

    @Override
    public Join<T, X> getJoin() {
        return CollectionUtils.get(() -> root.join(name, getJoinType()));
    }

    @Override
    public Collection<C> getColumns() {
        return columns.stream().filter(column -> new ColumnImpl<X, C>(getJoin(), column).isValid()).collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isValid() {
        return getJoin() != null;
    }
}
