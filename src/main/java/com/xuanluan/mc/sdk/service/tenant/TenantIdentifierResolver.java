package com.xuanluan.mc.sdk.service.tenant;

import com.xuanluan.mc.sdk.service.constant.BaseConstant;
import com.xuanluan.mc.sdk.utils.StringUtils;
import lombok.Setter;
import org.hibernate.cfg.AvailableSettings;

import java.util.Map;
import java.util.function.Supplier;

@Setter
public class TenantIdentifierResolver implements ITenantIdentifierResolver {
    private String currentTenant = BaseConstant.CLIENT_ID;

    /**
     * switch to param tenant and process => after processed then rollback oldTenant
     *
     * @param tenant will switch
     */
    public <T> T switchInProcess(String tenant, Supplier<T> supplier) {
        String oldTenant = currentTenant;
        try {
            currentTenant = tenant;
            return supplier.get();
        } finally {
            currentTenant = oldTenant;
        }
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        return getCurrentTenant();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }

    public String getCurrentTenant() {
        return StringUtils.hasText(currentTenant) ? currentTenant : BaseConstant.CLIENT_ID;
    }
}
