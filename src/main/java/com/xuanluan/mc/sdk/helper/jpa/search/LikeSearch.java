package com.xuanluan.mc.sdk.helper.jpa.search;

import com.xuanluan.mc.sdk.domain.model.request.jpa.SearchColumns;
import com.xuanluan.mc.sdk.helper.jpa.base.AssociationImpl;
import com.xuanluan.mc.sdk.helper.jpa.base.ColumnImpl;
import com.xuanluan.mc.sdk.helper.jpa.base.IAssociation;
import com.xuanluan.mc.sdk.helper.jpa.base.IColumn;

import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LikeSearch<T> extends BaseSearch<T> {
    public LikeSearch(CriteriaBuilder criteriaBuilder, Root<T> root) {
        super(criteriaBuilder, root, JoinType.LEFT);
    }

    @Override
    public Predicate search(SearchColumns columns) {
        return null;
    }

    private List<Predicate> byLike(Collection<SearchColumns.Column> columns, String keyword) {
        return columns.stream()
                .map(column -> new ColumnImpl<>(root.getCorrelationParent(), column))
                .filter(IColumn::isValid)
                .map(searchCol -> criteriaBuilder.like(criteriaBuilder.lower(searchCol.getPath()), "%" + keyword + "%"))
                .collect(Collectors.toList());
    }

    private List<Predicate> byLikeAssociations(Map<String, Collection<SearchColumns.Column>> tColumns, String keyword) {
        return tColumns.entrySet().stream()
                .map(entry -> new AssociationImpl<>(root, entry.getKey(), entry.getValue(), joinType))
                .filter(IAssociation::isValid)
                .flatMap(association -> byLike(association.getColumns(), keyword).stream())
                .collect(Collectors.toList());
    }
}
