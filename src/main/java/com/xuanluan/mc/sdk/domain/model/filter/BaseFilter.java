package com.xuanluan.mc.sdk.domain.model.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class BaseFilter {
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createdAtFrom;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createdAtTo;
    private int maxResult;
    private int index;
    private Set<String> ids;
    private String search;
    private Boolean isActive;
    private String userId;

    public int getMaxResult() {
        if (this.maxResult <= 0) this.maxResult = 20;
        return this.maxResult;
    }

    public int getIndex() {
        if (this.index < 0) this.index = 0;
        return this.index;
    }
}
