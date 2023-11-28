package com.xuanluan.mc.sdk.repository;

import com.xuanluan.mc.sdk.domain.model.filter.BaseFilter;
import com.xuanluan.mc.sdk.domain.model.filter.ResultList;
import com.xuanluan.mc.sdk.utils.AssertUtils;
import com.xuanluan.mc.sdk.utils.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author Xuan Luan
 * @createdAt 9/13/2022
 */
public abstract class BaseRepository<T> {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
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

    private boolean checkInstanceofField(String nameField, Object valueField) {
        boolean flag = false;
        if (StringUtils.hasTextAfterTrim(nameField)) {
            if (valueField instanceof String) {
                if (!StringUtils.hasTextAfterTrim((String) valueField) || valueField.equals("null")) {
                    return false;
                }
            } else if (!(valueField instanceof Long) && !(valueField instanceof Double) && !(valueField instanceof Integer) && !(valueField instanceof Boolean)) {
                return false;
            }
            flag = true;
        }
        return flag;
    }

    protected Predicate filterEqualAnyField(String nameField, Object valueField) {
        return this.checkInstanceofField(nameField, valueField) ? this.builder.equal(this.root.get(nameField), valueField) : null;
    }

    protected Predicate filterNotEqualAnyField(String nameField, Object valueField) {
        return this.checkInstanceofField(nameField, valueField) ? this.builder.notEqual(this.root.get(nameField), valueField) : null;
    }

    protected Predicate filterLikeAnyField(String nameField, String searchKey) {
        return StringUtils.hasTextAfterTrim(nameField) && StringUtils.hasTextAfterTrim(searchKey) ? this.builder.like(this.root.get(nameField), "%" + searchKey + "%") : null;
    }

    protected List<Predicate> getFilters(String clientId) {
        AssertUtils.notBlank(clientId, "client");
        return appendFilter("clientId", clientId, new LinkedList<>());
    }

    protected List<Predicate> getFilters(String clientId, String orgId) {
        AssertUtils.notBlank(orgId, "organization");
        return appendFilter("orgId", orgId, this.getFilters(clientId));
    }

    private List<Predicate> filterSearch(String clientId, Set<String> searchFilters, BaseFilter filter) {
        AssertUtils.notNull(filter, "filter");
        List<Predicate> predicates = new LinkedList<>();
        appendFilter("clientId", clientId, predicates);
        appendFilter(root.get("id").in(filter.getIds()), filter.getIds(), predicates);
        appendFilter("isActive", filter.getIsActive(), predicates);
        appendFilter(builder.greaterThanOrEqualTo(root.get("createdAt"), filter.getCreatedAtFrom()), filter.getCreatedAtFrom(), predicates);
        appendFilter(builder.lessThanOrEqualTo(root.get("createdAt"), filter.getCreatedAtTo()), filter.getCreatedAtTo(), predicates);
        //find record of userId
        appendFilter(builder.or(root.get("createdBy").in(filter.getUserIds()), root.get("updatedBy").in(filter.getUserIds())), filter.getUserIds(), predicates);
        if (StringUtils.hasTextAfterTrim(filter.getSearch()) && searchFilters != null) {
            Predicate predicate = null;
            for (String key : searchFilters) {
                predicate = predicate != null ? builder.or(predicate, this.filterLikeAnyField(key, filter.getSearch())) : this.filterLikeAnyField(key, filter.getSearch());
            }
            if (predicate != null) predicates.add(predicate);
        }
        return predicates;
    }

    protected List<Predicate> getFilterSearch(String clientId, Set<String> searchFilters, BaseFilter filter) {
        return this.filterSearch(clientId, searchFilters, filter);
    }

    protected List<Predicate> getFilterSearch(String clientId, String orgId, Set<String> searchFilters, BaseFilter filter) {
        List<Predicate> filters = this.getFilterSearch(clientId, searchFilters, filter);
        appendFilter("orgId", orgId, filters);
        return filters;
    }

    protected List<Predicate> appendFilter(String nameField, Object valueField, List<Predicate> predicates) {
        Predicate predicate = this.filterEqualAnyField(nameField, valueField);
        if (predicate != null) predicates.add(predicate);
        return predicates;
    }

    protected List<Predicate> appendFilter(Predicate predicate, Object value, List<Predicate> predicates) {
        return appendFilter(predicate, List.of(value), predicates);
    }

    protected List<Predicate> appendFilter(Predicate predicate, Collection<Object> values, List<Predicate> predicates) {
        Assert.notNull(predicate, "predicate must be not null");
        if (values != null && !values.isEmpty()) predicates.add(predicate);
        return predicates;
    }

    protected ResultList<T> getResultList(List<Predicate> filters, int index, int maxResult) {
        long totalRecord = getCount(filters);
        ResultList<T> resultList = new ResultList<>();
        resultList.setMaxResult(maxResult);
        resultList.setTotal(totalRecord);
        resultList.setIndex(index);
        if (totalRecord > 0L) {
            int offset = index * maxResult;
            List<T> elements = this.getListResult(filters, maxResult, offset, Sort.by(Sort.Direction.DESC, "updatedAt", "createdAt"));
            resultList.setResultList(elements);
        }
        resultList.setResultList(resultList.getResultList() != null ? resultList.getResultList() : new ArrayList<>());
        return resultList;
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
        List<T> elementList = this.getListResult(filters, 1, 0, null);
        return CollectionUtils.isEmpty(elementList) ? null : elementList.get(0);
    }

    protected T getSingleResult(List<Predicate> filters, Sort sort) {
        List<T> elementList = this.getListResult(filters, 1, 0, sort);
        return CollectionUtils.isEmpty(elementList) ? null : elementList.get(0);
    }

    protected List<T> getListResult(List<Predicate> filters) {
        return this.getListResult(filters, 0, 0, null);
    }

    protected List<T> getListResult(List<Predicate> filters, int maxResult, int offset, Sort sort) {
        this.query.where(filters.toArray(new Predicate[0]));
        if (sort != null) {
            this.query.orderBy(QueryUtils.toOrders(sort, this.root, this.builder));
        }
        return this.getListResult(this.query, maxResult, offset);
    }

    private List<T> getListResult(CriteriaQuery<T> criteriaQuery, int maxResult, int offset) {
        TypedQuery<T> typedQuery = this.entityManager.createQuery(criteriaQuery);
        if (maxResult > 0) typedQuery.setMaxResults(maxResult);
        if (offset > 0) typedQuery.setFirstResult(offset);
        return typedQuery.getResultList();
    }
}
