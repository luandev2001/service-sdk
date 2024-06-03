package com.xuanluan.mc.sdk.helper.jpa.search;

import com.xuanluan.mc.sdk.domain.model.request.jpa.SearchColumns;

import javax.persistence.criteria.Predicate;

public interface ISearch {
    Predicate search(SearchColumns columns);
}
