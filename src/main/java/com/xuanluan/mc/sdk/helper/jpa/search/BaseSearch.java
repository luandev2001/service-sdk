package com.xuanluan.mc.sdk.helper.jpa.search;

import com.xuanluan.mc.sdk.helper.jpa.BaseJpa;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

public abstract class BaseSearch<T> extends BaseJpa<T> implements ISearch {
    public BaseSearch(CriteriaBuilder criteriaBuilder, Root<T> root, JoinType joinType) {
        super(criteriaBuilder, root, JoinType.LEFT);
    }
}
