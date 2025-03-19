package com.xuanluan.mc.sdk.repository.specification;

import com.xuanluan.mc.sdk.model.request.page.FilterParameter;
import com.xuanluan.mc.sdk.model.request.page.SortParameter;
import com.xuanluan.mc.sdk.utils.RepositoryUtils;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.List;

@Builder
public class DynamicSpecification<T> implements Specification<T> {
    private final List<FilterParameter> filters;
    private final List<SortParameter> sorts;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.orderBy(RepositoryUtils.getOrders(root, criteriaBuilder).apply(sorts));
        return RepositoryUtils.getPredicate(root, criteriaBuilder).apply(filters);
    }
}
