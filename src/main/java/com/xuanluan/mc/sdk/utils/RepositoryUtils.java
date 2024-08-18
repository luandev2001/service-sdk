package com.xuanluan.mc.sdk.utils;

import org.springframework.data.domain.Sort;

import javax.persistence.metamodel.EntityType;

public class RepositoryUtils {
    public static Sort.Direction convertDirection(String direction) {
        return Sort.Direction.ASC.name().equals(direction.toUpperCase()) ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

    public static String getPrimaryKey(EntityType<?> model) {
        return model.getId(model.getIdType().getJavaType()).getName();
    }
}
