package com.xuanluan.mc.sdk.domain.model.filter;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResultList<T> {
    private long total;
    private List<T> resultList;
    private long index;
    private long maxResult;
}
