package com.xuanluan.mc.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author Xuan Luan
 * @createdAt 12/10/2022
 */
@Getter
@Setter
@Entity
public class DataSequence extends BaseEntity {
    private String clientId;
    private String orgId;
    private String className;
    private String sequenceValue;
    private int type;
}
