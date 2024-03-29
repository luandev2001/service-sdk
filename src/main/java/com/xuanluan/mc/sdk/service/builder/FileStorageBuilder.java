package com.xuanluan.mc.sdk.service.builder;

import com.xuanluan.mc.sdk.domain.entity.FileStorage;
import com.xuanluan.mc.sdk.domain.model.request.FileEntityRequest;
import com.xuanluan.mc.sdk.domain.model.request.FileRequest;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Assert;

/**
 * @author Xuan Luan
 * @createdAt 4/16/2023
 */
public class FileStorageBuilder<T> {
    private final String orgId;
    private final FileEntityRequest<T> fileEntity;

    public FileStorageBuilder(String orgId, FileEntityRequest<T> fileEntity) {
        Assert.notNull(orgId, "organization");
        Assert.notNull(fileEntity, "request");
        Assert.notNull(fileEntity.getEntityClass(), "object");
        Assert.notNull(fileEntity.getEntityId(), "object_id");
        Assert.notNull(fileEntity.getFile(), "request");
        this.orgId = orgId;
        this.fileEntity = fileEntity;
    }

    public FileStorage init() {
        FileRequest fileRequest = fileEntity.getFile();
        FileStorage fileStorage = FileStorage.builder()
                .orgId(orgId)
                .entityClass(fileEntity.getEntityClass().getName())
                .entityId(fileEntity.getEntityId())
                .data(Base64.decodeBase64(fileRequest.getData()))
                .name(fileRequest.getName())
                .originFile(fileRequest.getOriginalFile())
                .size(fileRequest.getSize())
                .type(fileRequest.getType())
                .build();
        fileStorage.setCreatedBy(fileEntity.getByUser());
        return fileStorage;
    }
}
