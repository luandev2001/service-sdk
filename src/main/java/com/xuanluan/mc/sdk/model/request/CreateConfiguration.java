package com.xuanluan.mc.sdk.model.request;

import com.xuanluan.mc.sdk.model.enums.DataType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateConfiguration {
    private String name;
    private Object value;
    private String type;
    private boolean isEdit = true;
    private DataType dataType;
}
