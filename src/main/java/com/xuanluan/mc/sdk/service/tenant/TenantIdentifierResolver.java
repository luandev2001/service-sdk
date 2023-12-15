package com.xuanluan.mc.sdk.service.tenant;

import com.xuanluan.mc.sdk.service.constant.BaseConstant;
import com.xuanluan.mc.sdk.utils.StringUtils;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {
    @Override
    public String resolveCurrentTenantIdentifier() {
        return StringUtils.hasText(TenantContext.getCurrentTenant()) ? TenantContext.getCurrentTenant() : BaseConstant.clientId;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
