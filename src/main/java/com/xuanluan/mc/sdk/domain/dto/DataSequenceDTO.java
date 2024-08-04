package com.xuanluan.mc.sdk.domain.dto;

import com.xuanluan.mc.sdk.domain.enums.SequenceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataSequenceDTO {
    private String value;
    private SequenceType type;
}
