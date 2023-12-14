package com.xuanluan.mc.sdk.service.tenant;

import com.xuanluan.mc.sdk.service.constant.BaseConstant;
import com.xuanluan.mc.sdk.utils.AssertUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
public class TenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl{
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
}
