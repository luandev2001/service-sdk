package com.xuanluan.mc.sdk.pattern.jpa.strategy.operator;

import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import java.util.List;

@RequiredArgsConstructor
public abstract class BaseOperatorStrategy implements OperatorStrategy {
    protected final From<?, ?> from;
    protected final CriteriaBuilder builder;

    protected abstract List<Class<?>> getValidTypes();

    @Override
    public void validate(String key, Object value) {
        Assert.notNull(key, "key must not be null");
        Assert.isTrue(getValidTypes().stream().anyMatch(
                        type -> type.isInstance(value)),
                String.format("Invalid instance of %s operator", getOperator().getSymbol())
        );
    }

    protected <Y> Path<Y> getAttribute(String key) {
        Path<Y> attribute = from.get(key);
        Assert.notNull(attribute, "Not found column: " + key);
        return attribute;
    }
}
