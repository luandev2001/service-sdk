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
    @Getter
    private String createdBy;
    @Getter
    private String updatedBy;
    private int maxResult = 20;
    private int offset = 0;
    @Getter
    private String id;
    @Getter
    private String search;
    @Getter
    private Boolean isActive;

    public int getMaxResult() {
        if (this.maxResult <= 0) {
            this.maxResult = 20;
        }

        return this.maxResult;
    }

    public int getOffset() {
        if (this.offset < 0) {
            this.offset = 0;
        }
        return this.offset;
    }
}
