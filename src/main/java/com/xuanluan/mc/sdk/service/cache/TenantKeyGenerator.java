package com.xuanluan.mc.sdk.service.cache;

import com.xuanluan.mc.sdk.service.tenant.ITenantIdentifierResolver;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TenantKeyGenerator extends BaseKeyGenerator {
    private final ITenantIdentifierResolver tenantIdentifierResolver;

    @Override
    public String generate(Object... params) {
        return tenantIdentifierResolver.getCurrentTenant() + ":" + super.generate(params);
    }
}
