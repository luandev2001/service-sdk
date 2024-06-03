package com.xuanluan.mc.sdk.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(value = BaseEntityListener.class)
public class BaseEntity {
    @Id
    private String id;
    @Column(updatable = false)
    private String createdBy;
    private String updatedBy;
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date updatedAt;
    private boolean isActive = true;
}
