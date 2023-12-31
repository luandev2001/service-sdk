package com.xuanluan.mc.sdk.service;

import java.util.List;

public interface ITenantService {
    List<String> getSchemas();

    boolean contains(String schema);
}
