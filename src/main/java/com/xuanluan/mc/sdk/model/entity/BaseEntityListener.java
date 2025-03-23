package com.xuanluan.mc.sdk.model.entity;

import com.xuanluan.mc.sdk.utils.StringUtils;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

public class BaseEntityListener {
    @PrePersist
    public void prePersist(BaseEntity entity) {
        if (entity.getId() == null) entity.setId(StringUtils.generateId());
        if (entity.getCreatedAt() == null) entity.setCreatedAt(Instant.now());
        if (entity.getUpdatedAt() == null) entity.setUpdatedAt(Instant.now());
    }

    @PreUpdate
    public void preUpdate(BaseEntity entity) {
        entity.setUpdatedAt(Instant.now());
    }
}
