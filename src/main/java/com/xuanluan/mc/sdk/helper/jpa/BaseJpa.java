package com.xuanluan.mc.sdk.helper.jpa;

import com.xuanluan.mc.sdk.helper.jpa.base.IBase;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

public abstract class BaseJpa<T> {
    protected final CriteriaBuilder criteriaBuilder;
    protected final Root<T> root;
    protected final JoinType joinType;

    public BaseJpa(CriteriaBuilder criteriaBuilder, Root<T> root, JoinType joinType) {
        this.criteriaBuilder = criteriaBuilder;
        this.root = root;
        this.joinType = joinType;
    }
}
