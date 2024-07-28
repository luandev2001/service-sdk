package com.xuanluan.mc.sdk.domain.entity;

import com.xuanluan.mc.sdk.domain.enums.SequenceType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;

/**
 * @author Xuan Luan
 * @createdAt 12/10/2022
 */
@Getter
@Setter
@Entity
public class DataSequence extends BaseEntity {
    @Column(nullable = false, updatable = false)
    private String className;
    @Column(nullable = false)
    private String sequenceValue;
    @Column(updatable = false)
    @Enumerated
    private SequenceType type;
}
