package com.xuanluan.mc.sdk.domain.model.request;

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
public class FileEntityRequest<T> {
    private String entityId;
    private Class<T> entityClass;
    private FileRequest file;
    private String byUser;
}
