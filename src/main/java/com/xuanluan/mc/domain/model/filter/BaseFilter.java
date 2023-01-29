package com.xuanluan.mc.domain.model.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class BaseFilter {
    @JsonFormat(
            shape = JsonFormat.Shape.NUMBER
    )
    private Date createdAtFrom;
    @JsonFormat(
            shape = JsonFormat.Shape.NUMBER
    )
    private Date createdAtTo;
    private String createdBy;
    private int maxResult = 20;
    private int offset = 0;
    private String id;
    private String search;

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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAtFrom() {
        return createdAtFrom;
    }

    public void setCreatedAtFrom(Date createdAtFrom) {
        this.createdAtFrom = createdAtFrom;
    }

    public Date getCreatedAtTo() {
        return createdAtTo;
    }

    public void setCreatedAtTo(Date createdAtTo) {
        this.createdAtTo = createdAtTo;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
