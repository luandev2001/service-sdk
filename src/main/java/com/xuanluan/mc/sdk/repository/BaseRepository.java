package com.xuanluan.mc.sdk.repository;

import com.xuanluan.mc.sdk.domain.model.filter.BaseFilter;
import com.xuanluan.mc.sdk.utils.CollectionUtils;
import com.xuanluan.mc.sdk.utils.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.function.Function;

/**
 * @author Xuan Luan
 * @createdAt 9/13/2022
 */
@Transactional(readOnly = true)
public class BaseRepository<T> {
    protected final EntityManager entityManager;
    protected final Class<T> tClass;
    protected CriteriaBuilder builder;
    protected CriteriaQuery<T> query;
    protected Root<T> root;

    protected BaseRepository(EntityManager entityManager, Class<T> tClass) {
        this.tClass = tClass;
        this.entityManager = entityManager;
    }

    protected void refresh() {
        this.builder = entityManager.getCriteriaBuilder();
        this.query = this.builder.createQuery(this.tClass);
        this.root = this.query.from(this.tClass);
        root.alias(tClass.getSimpleName());
    }


    protected Predicate likeOperator(String nameField, String searchKey) {
        return StringUtils.hasText(nameField) && StringUtils.hasText(searchKey) ? builder.like(root.get(nameField), "%" + searchKey + "%") : null;
    }

    protected List<Predicate> filterSearch(Set<String> searchFilters, BaseFilter filter) {
        Assert.notNull(filter, "filter must not null");
        List<Predicate> predicates = new LinkedList<>();
        if (StringUtils.hasText(filter.getKeyword()) && searchFilters != null) {
            Predicate predicate = null;
            for (String key : searchFilters) {
                predicate = predicate != null ? builder.or(predicate, this.likeOperator(key, filter.getKeyword())) : this.likeOperator(key, filter.getKeyword());
            }
            if (predicate != null) predicates.add(predicate);
        }
        return predicates;
    }

    protected List<Predicate> appendFilter(String nameField, Object valueField, List<Predicate> predicates) {
        if (StringUtils.hasText(nameField) && valueField != null) {
            predicates.add(builder.equal(root.get(nameField), valueField));
        }
        return predicates;
    }

    protected Function<Predicate, List<Predicate>> appendFilter(Collection<?> values, List<Predicate> predicates) {
        return predicate -> {
            if (CollectionUtils.isEmpty(values)) predicates.add(predicate);
            return predicates;
        };
    }

    protected Page<T> getResultList(List<Predicate> filters, int page, int size) {
        if (page < 0) page = 0;
        if (size < 0) size = 0;

        long totalRecord = getCount(filters);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedAt"));
        return PageableExecutionUtils.getPage(
                totalRecord > 0 ? getListResult(filters, pageable) : new ArrayList<>(),
                pageable,
                () -> totalRecord
        );
    }

    private long getCount(List<Predicate> filters) {
        CriteriaQuery<Long> queryL = builder.createQuery(Long.class);
        Root<T> rootL = queryL.from(query.getResultType());
        rootL.alias(root.getAlias());
        queryL.select(builder.count(rootL)).where(filters.toArray(new Predicate[]{}));
        try {
            return entityManager.createQuery(queryL).getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }

    protected T getSingleResult(List<Predicate> filters) {
        List<T> elementList = this.getListResult(filters, PageRequest.of(0, 1));
        return CollectionUtils.isEmpty(elementList) ? null : elementList.get(0);
    }

    protected T getSingleResult(List<Predicate> filters, Sort sort) {
        List<T> elementList = this.getListResult(filters, PageRequest.of(0, 1, sort));
        return CollectionUtils.isEmpty(elementList) ? null : elementList.get(0);
    }

    protected List<T> getListResult(List<Predicate> filters) {
        return this.getListResult(filters, PageRequest.of(0, 0));
    }

    protected List<T> getListResult(List<Predicate> filters, Pageable pageable) {
        this.query.where(filters.toArray(new Predicate[0]))
                .orderBy(QueryUtils.toOrders(pageable.getSort(), this.root, this.builder));
        return this.entityManager.createQuery(this.query)
                .setMaxResults(pageable.getPageSize())
                .setFirstResult((int) pageable.getOffset()).getResultList();
    }
}
