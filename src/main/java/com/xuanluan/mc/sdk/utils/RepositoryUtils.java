package com.xuanluan.mc.sdk.utils;

import com.xuanluan.mc.sdk.model.request.page.FilterParameter;
import com.xuanluan.mc.sdk.model.request.page.KeywordParameter;
import com.xuanluan.mc.sdk.model.request.page.SortParameter;
import com.xuanluan.mc.sdk.pattern.jpa.factory.JpaOperatorFactory;
import com.xuanluan.mc.sdk.pattern.jpa.strategy.operator.LikeOperatorStrategy;
import com.xuanluan.mc.sdk.pattern.jpa.strategy.operator.IOperatorStrategy;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RepositoryUtils {
    public static String getPrimaryKey(EntityType<?> model) {
        return model.getId(model.getIdType().getJavaType()).getName();
    }

    public static Function<List<FilterParameter>, Predicate> toPredicate(From<?, ?> from, CriteriaBuilder builder) {
        return filters -> {
            if (CollectionUtils.isEmpty(filters)) return builder.and();

            return builder.and(
                    filters.stream()
                            .map(filter -> {
                                IOperatorStrategy operatorStrategy = JpaOperatorFactory.getInstance(filter.getOperator()).apply(from, builder);
                                return operatorStrategy.toPredicate(filter.getName(), filter.getValue());
                            })
                            .toArray(Predicate[]::new)
            );
        };
    }

    public static Function<List<SortParameter>, List<Order>> toOrders(From<?, ?> from, CriteriaBuilder builder) {
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

    public static BiFunction<String, KeywordParameter, Predicate> toSearchPredicate(From<?, ?> from, CriteriaBuilder builder) {
        return (keyword, parameter) -> {
            if (!StringUtils.hasText(keyword)) return builder.and();
            if (CollectionUtils.isEmpty(parameter.getNames())) return builder.and();

            return builder.and(
                    parameter.getNames().stream()
                            .map(name -> {
                                IOperatorStrategy operatorStrategy = new LikeOperatorStrategy(from, builder, parameter.isPrefix(), parameter.isSuffix(), parameter.isIgnoreCase());
                                return operatorStrategy.toPredicate(name, keyword);
                            })
                            .toArray(Predicate[]::new)
            );
        };
    }
}
