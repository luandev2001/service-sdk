package com.xuanluan.mc.sdk.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateConfiguration {
    private String name;
    private String type;
    private Object value;
}
