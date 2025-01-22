package com.xuanluan.mc.sdk.repository;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

public class BaseRepositoryV2<T, ID> extends SimpleJpaRepository<T, ID> {
    public BaseRepositoryV2(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }
}
