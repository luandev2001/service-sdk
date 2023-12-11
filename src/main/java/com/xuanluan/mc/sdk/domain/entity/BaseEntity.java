package com.xuanluan.mc.sdk.domain.entity;

import com.xuanluan.mc.sdk.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity extends EntityModel {
    @Id
    private String id;
}
