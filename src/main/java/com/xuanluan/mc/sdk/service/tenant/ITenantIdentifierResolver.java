package com.xuanluan.mc.sdk.service.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;

import java.util.function.Supplier;

public interface ITenantIdentifierResolver extends CurrentTenantIdentifierResolver, HibernatePropertiesCustomizer {
    <T> T switchInProcess(String tenant, Supplier<T> supplier);
}
