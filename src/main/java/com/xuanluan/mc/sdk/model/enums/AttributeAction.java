package com.xuanluan.mc.sdk.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class AttributeAction {
    @Getter
    @RequiredArgsConstructor
    public enum Operator {
        eq("="),
        ne("!="),
        lt("<"),
        lte("<="),
        gt(">"),
        gte(">="),
        in("IN"),
        not_in("NOT IN");

        private final String symbol;
    }
}
