package com.xuanluan.mc.sdk.pattern.jpa.strategy.operator;

import com.xuanluan.mc.sdk.model.enums.AttributeAction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.List;

public class InStrategy extends BaseOperatorStrategy {
    public InStrategy(From<?, ?> from, CriteriaBuilder builder) {
        super(from, builder);
    }

    @Override
    protected List<Class<?>> getValidTypes() {
        return List.of(Collection.class);
    }

    @Override
    public Predicate toPredicate(String key, Object value) {
        return getAttribute(key).in(value);
    }

    @Override
    public AttributeAction.Operator getOperator() {
        return AttributeAction.Operator.in;
    }
}
