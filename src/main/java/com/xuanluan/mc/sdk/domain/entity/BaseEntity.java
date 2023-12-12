package com.xuanluan.mc.sdk.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xuanluan.mc.sdk.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
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
    private Date createdAt = new Date();
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date updatedAt;
    private boolean isActive = true;

    @PrePersist
    private void setDefault() {
        if (id == null) id = StringUtils.generateId();
    }
}
