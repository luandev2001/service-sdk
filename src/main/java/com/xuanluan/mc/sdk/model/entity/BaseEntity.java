package com.xuanluan.mc.sdk.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(value = BaseEntityListener.class)
public class BaseEntity<T extends Serializable> {
    @Id
    @Column(updatable = false, nullable = false)
    private T id;
    @Column(updatable = false)
    private Instant createdAt;
    private Instant updatedAt;
}
