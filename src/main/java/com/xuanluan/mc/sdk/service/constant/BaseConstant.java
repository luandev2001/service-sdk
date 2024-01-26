package com.xuanluan.mc.sdk.service.constant;

import org.modelmapper.Condition;

import java.util.Set;

public class BaseConstant {
    public static final String clientId = "public";
    public static final String orgId = "public";
    public static final String byUser = "system";

    public static class CacheName {
        public static final String configuration = "configurations";
    }

    public static final String EVERYONE = "everyone/";
    public static final String ADMIN = "admin/";
    public static final String SUPER_ADMIN = "super_admin/";
    public static final String HOOK = "hook/";
    public static final String EMPLOYEE = "hook/";
}
