package com.xuanluan.mc.sdk.pattern.jpa.strategy.operator;

import com.xuanluan.mc.sdk.model.enums.AttributeAction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;

public class EqualStrategy extends BaseOperatorStrategy {
    public EqualStrategy(From<?, ?> from, CriteriaBuilder builder) {
        super(from, builder);
    }

    @Override
    protected List<Class<?>> getValidTypes() {
        return List.of(List.class, String.class, Date.class, Number.class);
    }

    @Override
    public Predicate toPredicate(String key, Object value) {
        return builder.equal(getAttribute(key), value);
    }

    @Override
    public AttributeAction.Operator getOperator() {
        return AttributeAction.Operator.eq;
    }
}
