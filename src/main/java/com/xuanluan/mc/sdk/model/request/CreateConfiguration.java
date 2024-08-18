package com.xuanluan.mc.sdk.model.request;

import com.xuanluan.mc.sdk.model.enums.DataType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateConfiguration {
    private String name;
    private Object value;
    private String type;
    private boolean isEdit;
    private DataType dataType;
}
