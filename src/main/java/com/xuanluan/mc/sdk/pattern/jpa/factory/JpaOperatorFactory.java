package com.xuanluan.mc.sdk.pattern.jpa.factory;

import com.xuanluan.mc.sdk.exception.UnsupportedException;
import com.xuanluan.mc.sdk.model.enums.AttributeAction;
import com.xuanluan.mc.sdk.pattern.jpa.strategy.operator.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import java.util.function.BiFunction;

public class JpaOperatorFactory {
    public static BiFunction<From<?, ?>, CriteriaBuilder, OperatorStrategy> getInstance(AttributeAction.Operator operator) {
        return ((from, builder) -> {
            switch (operator) {
                case eq:
                    return new EqualStrategy(from, builder);
                case ne:
                    return new NotEqualStrategy(from, builder);
                case in:
                    return new InStrategy(from, builder);
                case not_in:
                    return new NotInStrategy(from, builder);
                case gt:
                    return new GreaterThanStrategy(from, builder);
                case gte:
                    return new GreaterThanOrEqualStrategy(from, builder);
                case lt:
                    return new LessThanStrategy(from, builder);
                case lte:
                    return new LessThanOrEqualStrategy(from, builder);
                default:
                    throw new UnsupportedException("Not found operator");
            }
        });
    }
}
