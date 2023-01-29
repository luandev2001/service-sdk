package com.xuanluan.mc.repository;

import com.xuanluan.mc.domain.model.filter.BaseFilter;
import com.xuanluan.mc.domain.model.filter.ResultList;
import com.xuanluan.mc.utils.BaseStringUtils;
import com.xuanluan.mc.utils.DateUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Xuan Luan
 * @createdAt 9/13/2022
 */
public abstract class BaseRepository<T> {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    protected final EntityManager entityManager;
    protected final CriteriaBuilder builder;
    protected final CriteriaQuery<T> query;
    protected final Root<T> root;
    protected final Class<T> tClass;

    protected BaseRepository(EntityManager entityManager, Class<T> tClass) {
        this.tClass = tClass;
        this.entityManager = entityManager;
        this.builder = entityManager.getCriteriaBuilder();
        this.query = builder.createQuery(this.tClass);
        this.root = query.from(this.tClass);
    }

    private boolean checkInstanceofField(String nameField, Object valueField) {
        boolean flag = false;
        if (BaseStringUtils.hasTextAfterTrim(nameField)) {
            if (valueField instanceof String) {
                if (!BaseStringUtils.hasTextAfterTrim((String) valueField) || valueField.equals("null")) {
                    return false;
                }
            } else {
                if (!(valueField instanceof Long)
                        && !(valueField instanceof Double)
                        && !(valueField instanceof Integer)
                        && !(valueField instanceof Boolean)) {
                    return false;
                }
            }
            flag = true;
        }
        return flag;
    }

    protected Predicate filterEqualAnyField(String nameField, Object valueField) {
        return checkInstanceofField(nameField, valueField) ? this.builder.equal(this.root.get(nameField), valueField) : null;
    }

    protected Predicate filterNotEqualAnyField(String nameField, Object valueField) {
        return checkInstanceofField(nameField, valueField) ? this.builder.notEqual(this.root.get(nameField), valueField) : null;
    }

    protected Predicate filterLikeAnyField(String nameField, String searchKey) {

        return BaseStringUtils.hasTextAfterTrim(nameField) && BaseStringUtils.hasTextAfterTrim(searchKey)
                ? builder.like(root.get(nameField), "%" + searchKey + "%")
                : null;
    }

    protected List<Predicate> getFilters(String clientId) {
        Assert.isTrue(BaseStringUtils.hasTextAfterTrim(clientId), "clientId must be not null");

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("clientId"), clientId));

        return predicates;
    }

    protected List<Predicate> getFilters(String clientId, String orgId) {
        Assert.isTrue(BaseStringUtils.hasTextAfterTrim(clientId), "clientId must be not null");
        Assert.isTrue(BaseStringUtils.hasTextAfterTrim(orgId), "orgId must be not null");

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("clientId"), clientId));
        predicates.add(builder.equal(root.get("orgId"), orgId));

        return predicates;
    }

    private List<Predicate> filterSearch(String clientId, HashMap<String, String> searchFilters, BaseFilter filter) {
        Assert.notNull(filter, "Filter must be not null");

        List<Predicate> predicates = new ArrayList<>();
        if (BaseStringUtils.hasTextAfterTrim(clientId)) {
            predicates.add(builder.equal(root.get("clientId"), clientId));
        }
        if (BaseStringUtils.hasTextAfterTrim(filter.getId())) {
            predicates.add(builder.equal(root.get("id"), clientId));
        }

        if (null != filter.getCreatedBy()) {
            predicates.add(builder.equal(root.get("createdBy"), filter.getCreatedBy()));
        }
        if (null != filter.getCreatedAtFrom()) {
            predicates.add(
                    builder.greaterThan(
                            root.get("createdAt"),
                            DateUtils.getStartDay(filter.getCreatedAtFrom())));
        }

        if (null != filter.getCreatedAtTo()) {
            predicates.add(
                    builder.lessThan(
                            root.get("createdAt"),
                            DateUtils.getEndDay(filter.getCreatedAtTo())));
        }

        if (BaseStringUtils.hasTextAfterTrim(filter.getSearch()) && (searchFilters != null)) {
            Predicate predicate = null;

            for (Map.Entry<String, String> var1 : searchFilters.entrySet()) {
                if (predicate == null) {
                    predicate = filterLikeAnyField(var1.getKey(), var1.getValue());
                }
                predicate = builder.or(predicate, filterLikeAnyField(var1.getKey(), var1.getValue()));
            }

            if (predicate != null) {
                predicates.add(predicate);
            }
        }
        return predicates;
    }

    protected List<Predicate> getFilterSearch(String clientId, HashMap<String, String> searchFilters, BaseFilter filter) {

        return filterSearch(clientId, searchFilters, filter);
    }

    protected List<Predicate> getFilterSearch(String clientId, String orgId, HashMap<String, String> searchFilters, BaseFilter filter) {
        List<Predicate> filters = filterSearch(clientId, searchFilters, filter);
        Assert.isTrue(BaseStringUtils.hasTextAfterTrim(orgId), "orgId must be not null");
        filters.add(builder.equal(root.get("orgId"), orgId));
        return filters;
    }

    protected List<Predicate> appendFilter(String nameField, String valueField, List<Predicate> filter) {

        Predicate predicate = filterEqualAnyField(nameField, valueField);
        if (predicate != null) {
            filter.add(predicate);
        }
        return filter;
    }

    protected ResultList<T> getResultList(List<Predicate> filters, int offset, int maxResult) {

        query.orderBy(builder.desc(root.get("createdAt")));

        List<T> elements = getListResult(filters);
        long totalRecord = elements.size();

        ResultList<T> resultList = new ResultList<>();
        resultList.setIndex(offset);
        resultList.setMaxResult(maxResult);
        resultList.setTotal(totalRecord);

        if (totalRecord > 0) {
            long skip = maxResult * offset;
            //element after set maxResult and index
            elements = elements.stream().skip(skip).limit(maxResult).collect(Collectors.toList());
            resultList.setResultList(elements);
        }

        return resultList;
    }

    protected T getSingleResult(List<Predicate> filters) {
        List<T> elementList = getListResult(filters);
        return CollectionUtils.isEmpty(elementList) ? null : elementList.get(0);
    }

    protected T getSingleResult(Predicate filter) {
        List<T> elementList = getListResult(filter);
        return CollectionUtils.isEmpty(elementList) ? null : elementList.get(0);
    }

    protected List<T> getListResult(List<Predicate> filters) {
        query.where(filters.toArray(new Predicate[]{}));

        return entityManager.createQuery(query).getResultList();
    }

    protected List<T> getListResult(Predicate filter) {
        query.where(filter);

        return entityManager.createQuery(query).getResultList();
    }
}
