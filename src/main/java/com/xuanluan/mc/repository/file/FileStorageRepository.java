package com.xuanluan.mc.repository.file;

import com.xuanluan.mc.domain.entity.FileStorage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Xuan Luan
 * @createdAt 4/16/2023
 */
@Repository
public interface FileStorageRepository extends CrudRepository<FileStorage, String> {
}
