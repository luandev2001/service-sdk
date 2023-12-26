package com.xuanluan.mc.sdk.service.tenant;

import org.flywaydb.core.Flyway;

public interface ITenantProvider {
    Flyway create(String schema, String[] locations);

    Flyway create(String schema);

    void delete(String schema);
}
