package com.xuanluan.mc.domain.model.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
public class BaseFilter {
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Getter
    private Date createdAtFrom;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Getter
    private Date createdAtTo;
    private int maxResult;
    private int index;
    @Getter
    private String id;
    @Getter
    private String search;
    @Getter
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
