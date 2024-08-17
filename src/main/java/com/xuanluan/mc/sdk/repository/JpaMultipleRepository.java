package com.xuanluan.mc.sdk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaMultipleRepository<T, ID>
        extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
}
