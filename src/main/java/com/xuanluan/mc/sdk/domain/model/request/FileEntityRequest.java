package com.xuanluan.mc.sdk.domain.model.request;

import com.xuanluan.mc.sdk.domain.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Xuan Luan
 * @createdAt 4/16/2023
 */
@Getter
@Setter
@Builder
public class FileEntityRequest<T extends BaseEntity> {
    private String entityId;
    private Class<T> entityClass;
    private FileRequest file;
}
