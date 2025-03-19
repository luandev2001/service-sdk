package com.xuanluan.mc.sdk.model.request.page;

import com.xuanluan.mc.sdk.model.enums.AttributeAction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterParameter {
    private String name;
    private Object value;
    private AttributeAction.Operator operator;
}
