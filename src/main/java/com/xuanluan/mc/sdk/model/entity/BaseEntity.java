package com.xuanluan.mc.sdk.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(value = BaseEntityListener.class)
public class BaseEntity {
    @Id
    @Column(length = 36)
    private String id;
    @Column(updatable = false)
    private Instant createdAt;
    private Instant updatedAt;
}
