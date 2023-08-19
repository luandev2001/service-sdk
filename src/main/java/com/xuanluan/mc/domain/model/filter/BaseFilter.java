package com.xuanluan.mc.domain.model.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BaseFilter {
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createdAtFrom;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createdAtTo;
    private int maxResult;
    private int index;
    private String id;
    private String search;
    private Boolean isActive;

    public int getMaxResult() {
        if (this.maxResult <= 0) this.maxResult = 20;
        return this.maxResult;
    }

    public int getIndex() {
        if (this.index < 0) this.index = 0;
        return this.index;
    }
}
