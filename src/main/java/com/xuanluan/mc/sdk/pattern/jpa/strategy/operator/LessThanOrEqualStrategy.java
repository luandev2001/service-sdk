package com.xuanluan.mc.sdk.pattern.jpa.strategy.operator;

import com.xuanluan.mc.sdk.model.enums.AttributeAction;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

public class LessThanOrEqualStrategy extends ComparableStrategy {
    public LessThanOrEqualStrategy(From<?, ?> from, CriteriaBuilder builder) {
        super(from, builder);
    }

    @Override
    protected <X extends Comparable<X>> Predicate getOperatorPredicate(Path<X> attribute, X value) {
        return builder.lessThan(attribute, value);
    }

    @Override
    protected List<Class<?>> getValidTypes() {
        return List.of(Number.class, Date.class);
    }

    @Override
    public Predicate toPredicate(String key, Object value) {
        return buildPredicate(key, value);
    }

    @Override
    public AttributeAction.Operator getOperator() {
        return AttributeAction.Operator.lte;
    }
}
