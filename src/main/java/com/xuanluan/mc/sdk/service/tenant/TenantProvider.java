package com.xuanluan.mc.sdk.service.tenant;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

@RequiredArgsConstructor
public class TenantProvider implements ITenantProvider {
    private final DataSource dataSource;
    private final String[] schema_locations;

    @Override
    public Flyway create(String schema, String[] locations) {
        Flyway flyway = Flyway.configure()
                .locations(locations)
                .dataSource(dataSource)
                .schemas(schema)
                .baselineOnMigrate(true)
                .load();
        flyway.migrate();
        return flyway;
    }

    @Override
    public Flyway create(String schema) {
        return create(schema, schema_locations);
    }

    @Override
    public void delete(String schema) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .schemas(schema)
                .load();
        flyway.clean();
    }
}
