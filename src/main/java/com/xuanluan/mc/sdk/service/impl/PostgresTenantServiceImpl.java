package com.xuanluan.mc.sdk.service.impl;

import com.xuanluan.mc.sdk.service.ITenantService;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class PostgresTenantServiceImpl implements ITenantService {
    private final EntityManager entityManager;

    @Override
    public List<String> getSchemas() {
        return getSchemas("nspname not like 'pg_%'");
    }

    @Override
    public boolean contains(String schema) {
        return !getSchemas(String.format("nspname = '%s'", schema)).isEmpty();
    }

    @SuppressWarnings("unchecked")
    private List<String> getSchemas(String condition) {
        String query = "SELECT nspname AS schema_name FROM pg_catalog.pg_namespace WHERE " + condition;
        return (List<String>) entityManager.createNativeQuery(query).getResultList();
    }
}
