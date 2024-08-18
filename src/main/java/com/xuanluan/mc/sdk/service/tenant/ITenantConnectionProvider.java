package com.xuanluan.mc.sdk.service.tenant;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;

public interface ITenantConnectionProvider extends MultiTenantConnectionProvider, HibernatePropertiesCustomizer {
}
