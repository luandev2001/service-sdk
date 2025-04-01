package com.xuanluan.mc.sdk.pattern.jpa.strategy.operator;

import com.xuanluan.mc.sdk.model.enums.AttributeAction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.List;

public class LikeOperatorStrategy extends BaseOperatorStrategy {
    private final String prefix;
    private final String suffix;
    private final boolean isIgnoreCase;

    public LikeOperatorStrategy(From<?, ?> from, CriteriaBuilder builder) {
        this(from, builder, false, false, true);
    }

    public LikeOperatorStrategy(From<?, ?> from, CriteriaBuilder builder, boolean isPrefix, boolean isSuffix, boolean isIgnoreCase) {
        super(from, builder);
        this.prefix = isSuffix ? "%" : "";
        this.suffix = isPrefix ? "%" : "";
        this.isIgnoreCase = isIgnoreCase;
    }

    @Override
    protected List<Class<?>> getValidTypes() {
        return List.of(String.class);
    }

    @Override
    public Predicate toPredicate(String key, Object value) {
        Expression<String> likeExpr = getAttribute(key);
        String keywordValue = value.toString();
        if (isIgnoreCase) {
            likeExpr = builder.lower(likeExpr);
            keywordValue = keywordValue.toLowerCase();
        }

        return builder.like(likeExpr, builder.literal(prefix + keywordValue + suffix));
    }

    @Override
    public AttributeAction.Operator getOperator() {
        return AttributeAction.Operator.like;
    }
}
