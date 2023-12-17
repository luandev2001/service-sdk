package com.xuanluan.mc.sdk.domain.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * @author Xuan Luan
 * @createdAt 12/30/2022
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class FileStorage extends BaseEntity {
    @Column(nullable = false, updatable = false)
    private String orgId;
    @Column(nullable = false, updatable = false)
    private String type; // png/jpg/...
    @Column(updatable = false)
    private String name;
    @Column(updatable = false)
    private String originFile;
    @Column(nullable = false, updatable = false)
    private long size;
    @Column(nullable = false, updatable = false)
    @Lob
    private byte[] data;
    private String entityId;
    private String entityClass;
}
