package com.xuanluan.mc.sdk.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xuanluan.mc.sdk.utils.BaseStringUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Id
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private String createdBy;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private String updatedBy;
    private Date createdAt;
    private Date updatedAt;
    private boolean isActive;

    protected BaseEntity() {
        id = BaseStringUtils.generateId();
        isActive = true;
        createdAt = new Date();
    }
}
