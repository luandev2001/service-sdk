package com.xuanluan.mc.sdk.repository;

import com.xuanluan.mc.sdk.model.request.jpa.QueryOptionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface IBaseJpaRepository<T> {
    List<T> findAll(Specification<T> spec, QueryOptionRequest request);

    Optional<T> findOne(Specification<T> spec, QueryOptionRequest request);

    Page<T> findAll(Specification<T> spec, Pageable pageable, QueryOptionRequest request);
}
