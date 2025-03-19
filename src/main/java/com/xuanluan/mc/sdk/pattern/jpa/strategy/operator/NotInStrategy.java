package com.xuanluan.mc.sdk.pattern.jpa.strategy.operator;

import com.xuanluan.mc.sdk.model.enums.AttributeAction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

public class NotInStrategy extends InStrategy{
    public NotInStrategy(From<?, ?> from, CriteriaBuilder builder) {
        super(from, builder);
    }

    @Override
    public Predicate toPredicate(String key, Object value) {
        return super.toPredicate(key, value).not();
    }

    @Override
    public AttributeAction.Operator getOperator() {
        return AttributeAction.Operator.not_in;
    }
}
