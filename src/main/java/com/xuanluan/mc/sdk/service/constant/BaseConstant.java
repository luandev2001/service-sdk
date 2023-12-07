package com.xuanluan.mc.sdk.service.constant;

import org.modelmapper.Condition;

import java.util.Set;

public class BaseConstant {
    public static final String clientId = "public";
    public static final String orgId = "public";
    public static final String byUser = "system";

    public static class Mapper {
        public static final Condition skipEntityFields = mappingContext -> {
            String name = mappingContext.getMapping().getLastDestinationProperty().getName();
            Set<String> skipFields = Set.of("id", "createdAt", "createdBy", "updatedAt", "updatedBy");
            return !skipFields.contains(name);
        };
    }
}