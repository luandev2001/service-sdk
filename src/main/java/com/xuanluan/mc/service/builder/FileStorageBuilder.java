package com.xuanluan.mc.service.builder;

import com.xuanluan.mc.domain.entity.BaseEntity;
import com.xuanluan.mc.domain.entity.FileStorage;
import com.xuanluan.mc.domain.model.request.FileEntityRequest;
import com.xuanluan.mc.domain.model.request.FileRequest;
import com.xuanluan.mc.utils.ExceptionUtils;
import org.apache.commons.codec.binary.Base64;

/**
 * @author Xuan Luan
 * @createdAt 4/16/2023
 */
public class FileStorageBuilder<T extends BaseEntity> {
    private final String clientId;
    private final String orgId;
    private final FileEntityRequest<T> fileEntity;

    public FileStorageBuilder(String clientId, String orgId, FileEntityRequest<T> fileEntity) {
        ExceptionUtils.notBlank("clientId", clientId);
        ExceptionUtils.notBlank("orgId", orgId);
        ExceptionUtils.notNull("fileEntity", fileEntity);
        ExceptionUtils.notNull("fileEntityClass", fileEntity.getEntityClass());
        ExceptionUtils.notBlank("fileEntityId", fileEntity.getEntityId());
        ExceptionUtils.notNull("fileRequest", fileEntity.getFile());
        this.clientId = clientId;
        this.orgId = orgId;
        this.fileEntity = fileEntity;
    }

    public FileStorage init() {
        FileRequest fileRequest = fileEntity.getFile();
        return FileStorage.builder()
                .clientId(clientId)
                .orgId(orgId)
                .entityClass(fileEntity.getEntityClass().getName())
                .entityId(fileEntity.getEntityId())
                .data(Base64.decodeBase64(fileRequest.getData()))
                .name(fileRequest.getName())
                .originFile(fileRequest.getOriginalFile())
                .size(fileRequest.getSize())
                .type(fileRequest.getType())
                .build();
    }
}
