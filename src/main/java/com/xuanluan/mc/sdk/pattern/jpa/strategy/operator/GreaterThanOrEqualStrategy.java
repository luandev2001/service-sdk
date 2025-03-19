package com.xuanluan.mc.sdk.pattern.jpa.strategy.operator;

import com.xuanluan.mc.sdk.model.enums.AttributeAction;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

public class GreaterThanOrEqualStrategy extends ComparableStrategy {

    public GreaterThanOrEqualStrategy(From<?, ?> from, CriteriaBuilder builder) {
        super(from, builder);
    }

    @Override
    protected <X extends Comparable<X>> Predicate getOperatorPredicate(Path<X> attribute, X value) {
        return builder.greaterThanOrEqualTo(attribute, value);
    }

    @Override
    protected List<Class<?>> getValidTypes() {
        return List.of(Number.class, Date.class);
    }

    @Override
    public void validate(String key, Object value) {
        super.validate(key, value);
    }

    @Override
    public Predicate toPredicate(String key, Object value) {
        return buildPredicate(key, value);
    }

    @Override
    public AttributeAction.Operator getOperator() {
        return AttributeAction.Operator.gte;
    }
}
