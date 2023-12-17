package com.xuanluan.mc.sdk.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xuanluan.mc.sdk.service.constant.BaseConstant;
import com.xuanluan.mc.sdk.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Id
    private String id;
    @Column(updatable = false)
    private String createdBy;
    private String updatedBy;
    @Column(nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date updatedAt;
    private boolean isActive = true;

    @PrePersist
    private void prePersist() {
        if (id == null) id = StringUtils.generateId();
        if (createdAt == null) createdAt = new Date();
        if (createdBy == null) createdBy = BaseConstant.byUser;
    }

    @PreUpdate
    public void preUpdate() {
        if (updatedAt == null) updatedAt = new Date();
        if (updatedBy == null) updatedBy = BaseConstant.byUser;
    }
}
