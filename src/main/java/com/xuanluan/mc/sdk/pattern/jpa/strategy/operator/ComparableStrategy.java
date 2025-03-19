package com.xuanluan.mc.sdk.pattern.jpa.strategy.operator;

import com.xuanluan.mc.sdk.exception.UnsupportedException;
import org.hibernate.query.criteria.internal.ValueHandlerFactory;

import javax.persistence.criteria.*;

public abstract class ComparableStrategy extends BaseOperatorStrategy {
    public ComparableStrategy(From<?, ?> from, CriteriaBuilder builder) {
        super(from, builder);
    }

    protected <X extends Comparable<X>> Predicate buildPredicate(String key, Object value) {
        try {
            Path<X> attribute = getAttribute(key);
            X convertedValue = ValueHandlerFactory.convert(value, attribute.getJavaType());
            return getOperatorPredicate(attribute, convertedValue);
        } catch (IllegalArgumentException e) {
            throw new UnsupportedException("Invalid value");
        }
    }

    protected abstract <X extends Comparable<X>> Predicate getOperatorPredicate(Path<X> attribute, X value);
}
