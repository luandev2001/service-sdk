package com.xuanluan.mc.sdk.domain.entity;

import com.xuanluan.mc.sdk.domain.enums.DataType;
import com.xuanluan.mc.sdk.service.converter.ObjectJpaConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Configuration extends BaseEntity {
    @Column(nullable = false, updatable = false)
    private String clientId;
    @Convert(converter = ObjectJpaConverter.class)
    private Object value;
    @Column(nullable = false, updatable = false, length = 50)
    private String name;
    @Column(nullable = false, updatable = false, length = 25)
    private String type;
    @Column(updatable = false)
    private boolean isEdit = true;
    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private DataType dataType = DataType.STRING;
}
