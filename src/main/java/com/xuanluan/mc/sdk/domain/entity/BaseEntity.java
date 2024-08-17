package com.xuanluan.mc.sdk.domain.entity;

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
    @Column(length = 36)
    private String id;
    @Column(updatable = false)
    private Date createdAt;
    private Date updatedAt;
}
