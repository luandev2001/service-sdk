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

public class TsVectorSearch<T> extends BaseSearch<T> {
    public TsVectorSearch(CriteriaBuilder criteriaBuilder, Root<T> root) {
        super(criteriaBuilder, root, JoinType.LEFT);
    }

    @Override
    public Predicate search(SearchColumns columns) {
        List<Expression<String>> expressions = byTsVector(columns.getColumns());
        expressions.addAll(byTsVectorAssociations(columns.getAssociatedColumns()));

        Expression<String> combinedExpression = expressions.stream()
                .reduce(
                        criteriaBuilder.literal(""),
                        (combined, expression) -> criteriaBuilder.function("concat", String.class, combined, expression)
                );
        Expression<String> tsqueryExpression = criteriaBuilder.function(
                "to_tsquery",
                String.class,
                criteriaBuilder.literal("simple"),
                criteriaBuilder.literal("' ' || " + columns.getKeyword() + " || ':*'")
        );

        return criteriaBuilder.isTrue(criteriaBuilder.function("ts_match_vq", Boolean.class, combinedExpression, tsqueryExpression));
    }

    private List<Expression<String>> byTsVector(Collection<SearchColumns.Column> columns) {
        return columns.stream()
                .map(column -> new ColumnImpl<>(root.getCorrelationParent(), column))
                .filter(IColumn::isValid)
                .map(searchCol -> buildTsvectorExpression(searchCol.getPath()))
                .collect(Collectors.toList());
    }

    private List<Expression<String>> byTsVectorAssociations(Map<String, Collection<SearchColumns.Column>> tColumns) {
        return tColumns.entrySet().stream()
                .map(entry -> new AssociationImpl<>(root, entry.getKey(), entry.getValue(), joinType))
                .filter(IAssociation::isValid)
                .flatMap(association -> byTsVector(association.getColumns()).stream())
                .collect(Collectors.toList());
    }

    private Expression<String> buildTsvectorExpression(Path<String> path) {
        return criteriaBuilder.function(
                "to_tsvector",
                String.class,
                criteriaBuilder.literal("simple"),
                criteriaBuilder.coalesce(path, criteriaBuilder.literal(""))
        );
    }
}
