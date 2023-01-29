package com.xuanluan.mc.domain.model.filter;

import java.util.ArrayList;
import java.util.List;

public class ResultList<T> {
    private long total = 0;
    private List<T> resultList = new ArrayList<>();
    private long index;
    private long maxResult;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public long getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(long maxResult) {
        this.maxResult = maxResult;
    }
}
