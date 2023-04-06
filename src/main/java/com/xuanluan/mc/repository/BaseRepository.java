package com.xuanluan.mc.repository;

import com.xuanluan.mc.domain.model.filter.BaseFilter;
import com.xuanluan.mc.domain.model.filter.ResultList;
import com.xuanluan.mc.utils.BaseStringUtils;
import com.xuanluan.mc.utils.DateUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

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
        if (BaseStringUtils.hasTextAfterTrim(nameField)) {
            if (valueField instanceof String) {
                if (!BaseStringUtils.hasTextAfterTrim((String) valueField) || valueField.equals("null")) {
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
        return BaseStringUtils.hasTextAfterTrim(nameField) && BaseStringUtils.hasTextAfterTrim(searchKey) ? this.builder.like(this.root.get(nameField), "%" + searchKey + "%") : null;
    }

    protected List<Predicate> getFilters(String clientId) {
        Assert.isTrue(BaseStringUtils.hasTextAfterTrim(clientId), "clientId must be not null");
        return appendFilter("clientId", clientId, new ArrayList<>());
    }

    protected List<Predicate> getFilters(String clientId, String orgId) {
        Assert.isTrue(BaseStringUtils.hasTextAfterTrim(orgId), "orgId must be not null");
        return appendFilter("orgId", orgId, this.getFilters(clientId));
    }

    private List<Predicate> filterSearch(String clientId, HashMap<String, String> searchFilters, BaseFilter filter) {
        Assert.notNull(filter, "Filter must be not null");
        List<Predicate> filters = new ArrayList<>();
        if (!"all".equals(clientId)) {
            appendFilter("clientId", clientId, filters);
            filters.add(this.filterNotEqualAnyField("clientId", "all"));
        }
        appendFilter("id", filter.getId(), filters);
        appendFilter("isActive", filter.getIsActive(), filters);
        if (null != filter.getCreatedAtFrom()) {
            filters.add(this.builder.greaterThan(this.root.get("createdAt"), DateUtils.getStartDay(filter.getCreatedAtFrom())));
        }
        if (null != filter.getCreatedAtTo()) {
            filters.add(this.builder.lessThan(this.root.get("createdAt"), DateUtils.getEndDay(filter.getCreatedAtTo())));
        }

        if (BaseStringUtils.hasTextAfterTrim(filter.getSearch()) && searchFilters != null) {
            Predicate predicate = null;
            Map.Entry<String, String> var1;
            for (Iterator<Map.Entry<String, String>> var6 = searchFilters.entrySet().iterator(); var6.hasNext(); predicate = this.builder.or(predicate, this.filterLikeAnyField(var1.getKey(), var1.getValue()))) {
                var1 = var6.next();
                if (predicate == null) {
                    predicate = this.filterLikeAnyField(var1.getKey(), var1.getValue());
                }
            }
            if (predicate != null) filters.add(predicate);
        }

        return filters;
    }

    protected List<Predicate> getFilterSearch(String clientId, HashMap<String, String> searchFilters, BaseFilter filter) {
        return this.filterSearch(clientId, searchFilters, filter);
    }

    protected List<Predicate> getFilterSearch(String clientId, String orgId, HashMap<String, String> searchFilters, BaseFilter filter) {
        List<Predicate> filters = this.filterSearch(clientId, searchFilters, filter);
        if (!"all".equals(orgId)) {
            appendFilter("orgId", orgId, filters);
            filters.add(this.filterNotEqualAnyField("orgId", "all"));
        }
        return filters;
    }

    protected List<Predicate> appendFilter(String nameField, Object valueField, List<Predicate> filter) {
        Predicate predicate = this.filterEqualAnyField(nameField, valueField);
        if (predicate != null) filter.add(predicate);
        return filter;
    }

    protected List<Predicate> appendFilter(Predicate predicate, List<Predicate> filter) {
        if (predicate != null) filter.add(predicate);
        return filter;
    }

    protected ResultList<T> getResultList(List<Predicate> filters, int index, int maxResult) {
        long totalRecord = getCount(filters);
        ResultList<T> resultList = new ResultList<>();
        resultList.setMaxResult(maxResult);
        resultList.setTotal(totalRecord);
        resultList.setIndex(index);
        if (totalRecord > 0L) {
            List<T> elements = this.getListResult(filters, maxResult, 0, Sort.by(Sort.Direction.DESC, "updatedAt", "createdAt"));
            if (index > 0) {
                int skip = (maxResult * index);
                elements = elements.stream().skip(skip).limit(maxResult).collect(Collectors.toList());
            }
            resultList.setResultList(elements);
        }
        return resultList;
    }

    public static void main(String[] args) {
        List<String> strings=List.of("1","2","3","4","5");
        System.out.println(strings.stream().skip(0).limit(2).collect(Collectors.toList()));
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
