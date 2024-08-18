package com.xuanluan.mc.sdk.model.dto;

import com.xuanluan.mc.sdk.model.enums.SequenceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataSequenceDTO {
    private String value;
    private SequenceType type;
}
