package com.xuanluan.mc.sdk.service.impl;

import com.xuanluan.mc.sdk.service.ITenantService;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class PostgresTenantServiceImpl implements ITenantService {
    private final EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getSchemas() {
        String query = "SELECT nspname AS schema_name\n" +
                "FROM pg_catalog.pg_namespace\n" +
                "WHERE nspname not like 'pg_%'";
        return (List<String>) entityManager.createNativeQuery(query).getResultList();
    }

    @Override
    public boolean contains(String schema) {
        return getSchemas().contains(schema);
    }
}
