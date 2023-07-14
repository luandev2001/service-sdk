package com.xuanluan.mc.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xuanluan.mc.utils.BaseStringUtils;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
