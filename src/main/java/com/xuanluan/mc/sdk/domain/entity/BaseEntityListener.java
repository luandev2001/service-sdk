package com.xuanluan.mc.sdk.domain.entity;

import com.xuanluan.mc.sdk.service.constant.BaseConstant;
import com.xuanluan.mc.sdk.utils.StringUtils;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

public class BaseEntityListener {
    @PrePersist
    public void prePersist(BaseEntity entity) {
        if (entity.getId() == null) entity.setId(StringUtils.generateId());
        if (entity.getCreatedAt() == null) entity.setCreatedAt(new Date());
        if (entity.getCreatedBy() == null) entity.setCreatedBy(BaseConstant.byUser);
    }

    @PreUpdate
    public void preUpdate(BaseEntity entity) {
        entity.setUpdatedAt(new Date());
        if (entity.getUpdatedBy() == null) entity.setUpdatedBy(BaseConstant.byUser);
    }
}
