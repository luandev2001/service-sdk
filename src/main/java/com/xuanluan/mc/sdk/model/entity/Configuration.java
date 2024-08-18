package com.xuanluan.mc.sdk.model.entity;

import com.xuanluan.mc.sdk.model.enums.DataType;
import com.xuanluan.mc.sdk.service.converter.ObjectJpaConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Configuration extends BaseEntity {
    @Convert(converter = ObjectJpaConverter.class)
    private Object value;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, length = 50)
    private String type;
    @Column(updatable = false)
    private boolean isEdit = true;
    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private DataType dataType = DataType.STRING;
}
