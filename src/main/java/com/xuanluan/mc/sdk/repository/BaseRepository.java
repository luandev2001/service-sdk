package com.xuanluan.mc.sdk.repository;

import com.xuanluan.mc.sdk.domain.model.filter.BaseFilter;
import com.xuanluan.mc.sdk.utils.CollectionUtils;
import com.xuanluan.mc.sdk.utils.RepositoryUtils;
import com.xuanluan.mc.sdk.utils.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
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

    protected List<Predicate> appendFilter(String nameField, Object valueField, List<Predicate> predicates) {
        Assert.isTrue(StringUtils.hasText(nameField), "nameField must not blank");
        return appendFilter(valueField, predicates).apply(builder.equal(root.get(nameField), valueField));
    }

    protected Function<Predicate, List<Predicate>> appendFilter(Object value, List<Predicate> predicates) {
        return predicate -> CollectionUtils.append(() -> value != null, predicates).apply(predicate);
    }

    protected Page<T> getPage(List<Predicate> predicates, Pageable pageable) {
        long totalRecord = getCount(predicates);
        return PageableExecutionUtils.getPage(
                totalRecord > 0 ? getListResult(predicates, pageable) : new ArrayList<>(),
                pageable,
                () -> totalRecord
        );
    }

    protected Page<T> getPage(List<Predicate> predicates, BaseFilter filter) {
        Set<String> columns = new HashSet<>(filter.getFilters().keySet());
        columns.addAll(filter.getSorts().keySet());

        EntityType<T> model = root.getModel();
        List<Sort.Order> orders = new LinkedList<>();
        columns.forEach(column -> {
            try {
                Object value = filter.getFilters().get(column);
                String direction = filter.getSorts().get(column);
                if (model.getAttribute(column).getPersistentAttributeType() == Attribute.PersistentAttributeType.BASIC) {
                    appendFilter(value, predicates).apply(root.get(column).in(value));
                    CollectionUtils.append(() -> StringUtils.hasText(direction), orders).apply(new Sort.Order(RepositoryUtils.convertDirection(direction), column));
                }
            } catch (Exception ignored) {
            }
        });
        return getPage(predicates, PageRequest.of(filter.getPage(), filter.getSize(), Sort.by(orders)));
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
        return CollectionUtils.firstElement(elementList);
    }

    protected T getSingleResult(List<Predicate> filters, Sort sort) {
        List<T> elementList = this.getListResult(filters, PageRequest.of(0, 1, sort));
        return CollectionUtils.firstElement(elementList);
    }

    protected List<T> getListResult(List<Predicate> filters) {
        return getListResult(filters, 0, 0, Sort.unsorted());
    }

    protected List<T> getListResult(List<Predicate> filters, Pageable pageable) {
        return getListResult(filters, (int) pageable.getOffset(), pageable.getPageSize(), pageable.getSort());
    }

    private List<T> getListResult(List<Predicate> filters, int offset, int size, Sort sort) {
        this.query.where(filters.toArray(new Predicate[0]))
                .orderBy(QueryUtils.toOrders(sort, this.root, this.builder));
        return this.entityManager.createQuery(this.query)
                .setMaxResults(size)
                .setFirstResult(offset)
                .getResultList();
    }
}
