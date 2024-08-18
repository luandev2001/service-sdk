package com.xuanluan.mc.sdk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface JpaMultipleRepository<T, ID>
        extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
}
