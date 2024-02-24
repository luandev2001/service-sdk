package com.xuanluan.mc.sdk.domain.model.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseFilter {
    private int size;
    private int page;
    private String keyword;

    public int getSize() {
        if (this.size <= 0) this.size = 20;
        return this.size;
    }
}
