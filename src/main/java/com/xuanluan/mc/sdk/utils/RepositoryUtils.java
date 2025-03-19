package com.xuanluan.mc.sdk.utils;

import com.xuanluan.mc.sdk.model.request.page.FilterParameter;
import com.xuanluan.mc.sdk.model.request.page.SortParameter;
import com.xuanluan.mc.sdk.pattern.jpa.factory.JpaOperatorFactory;
import com.xuanluan.mc.sdk.pattern.jpa.strategy.operator.OperatorStrategy;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RepositoryUtils {
    public static String getPrimaryKey(EntityType<?> model) {
        return model.getId(model.getIdType().getJavaType()).getName();
    }

    public static Function<List<FilterParameter>, Predicate> getPredicate(From<?, ?> from, CriteriaBuilder builder) {
        return filters -> {
            if (CollectionUtils.isEmpty(filters)) return builder.and();

            return builder.and(
                    filters.stream()
                            .map(filter -> {
                                OperatorStrategy operatorStrategy = JpaOperatorFactory.getInstance(filter.getOperator()).apply(from, builder);
                                return operatorStrategy.toPredicate(filter.getName(), filter.getValue());
                            })
                            .toArray(Predicate[]::new)
            );
        };
    }

    public static Function<List<SortParameter>, List<Order>> getOrders(From<?, ?> from, CriteriaBuilder builder) {
        return sorts -> {
            if (CollectionUtils.isEmpty(sorts)) return new ArrayList<>();

            return sorts.stream()
                    .map(sort -> {
                        Path<?> path = from.get(sort.getName());
                        return sort.getDirection() == Sort.Direction.ASC ? builder.asc(path) : builder.desc(path);
                    })
                    .collect(Collectors.toList());
        };
    }
}
