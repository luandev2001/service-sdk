package com.xuanluan.mc.sdk.service.tenant;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

@RequiredArgsConstructor
public class FlywayProvider {
    private final DataSource dataSource;

    public void initDatabase(String schema, String[] locations) {
        Flyway flyway = Flyway.configure()
                .locations(locations)
                .dataSource(dataSource)
                .schemas(schema)
                .load();
        flyway.migrate();
    }
}
