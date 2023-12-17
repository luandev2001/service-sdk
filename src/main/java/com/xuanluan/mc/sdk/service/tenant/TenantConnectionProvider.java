package com.xuanluan.mc.sdk.service.tenant;

import com.xuanluan.mc.sdk.service.constant.BaseConstant;
import com.xuanluan.mc.sdk.utils.AssertUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@RequiredArgsConstructor
public class TenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl implements HibernatePropertiesCustomizer {
    private final DataSource dataSource;

    @Override
    protected DataSource selectAnyDataSource() {
        return selectDataSource(BaseConstant.clientId);
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        try {
            Connection connection = dataSource.getConnection();
            connection.setSchema(tenantIdentifier);
        } catch (SQLException e) {
            AssertUtils.isTrue(false, "error.invalid_tenant", "");
        }
        return dataSource;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        connection.setSchema(BaseConstant.clientId);
        super.releaseConnection(tenantIdentifier, connection);
    }
}
