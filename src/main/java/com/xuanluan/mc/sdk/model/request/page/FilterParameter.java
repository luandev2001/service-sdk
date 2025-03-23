package com.xuanluan.mc.sdk.model.request.page;

import com.xuanluan.mc.sdk.model.enums.AttributeAction;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterParameter {
    private String name;
    private Object value;
    private AttributeAction.Operator operator;
}
