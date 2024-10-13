package com.xuanluan.mc.sdk.service.impl;

import com.xuanluan.mc.sdk.service.ITenantService;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
public class PostgresTenantServiceImpl implements ITenantService {
    private final EntityManager entityManager;
    private final List<String> excludeNames;

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getSchemas() {
        return querySchema("").getResultList();
    }

    @Override
    public boolean contains(String schema) {
        return !querySchema(String.format(" AND nspname = '%s'", schema))
                .setMaxResults(1).getResultList().isEmpty();
    }

    private Query querySchema(String condition) {
        String sql = "SELECT nspname FROM pg_catalog.pg_namespace WHERE nspname NOT IN :excludedNames" + condition;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("excludedNames", excludeNames);
        return query;
    }
}
