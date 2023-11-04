package com.xuanluan.mc.sdk.domain.entity;

import com.xuanluan.mc.sdk.service.converter.MapJpaConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

@Getter
@Setter
@Entity
public class Configuration extends BaseEntity {
    @Column(nullable = false, updatable = false)
    private String clientId;
    @Convert(converter = MapJpaConverter.class)
    private Map<String, Object> value;
    @Column(nullable = false, updatable = false, length = 50)
    private String name;
    @Column(nullable = false, updatable = false, length = 25)
    private String type;
    @Column(updatable = false)
    private boolean isEdit = true;
}
