package com.xuanluan.mc.sdk.pattern.jpa.strategy.operator;

import com.xuanluan.mc.sdk.model.enums.AttributeAction;

import javax.persistence.criteria.Predicate;

public interface OperatorStrategy {
    void validate(String key, Object value);

    Predicate toPredicate(String key, Object value);

    AttributeAction.Operator getOperator();
}
