package com.xuanluan.mc.sdk.repository.specification;

import com.xuanluan.mc.sdk.model.request.page.BasePageParameter;
import com.xuanluan.mc.sdk.utils.RepositoryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.List;

@RequiredArgsConstructor
public class DynamicSpecification<T> implements Specification<T> {
    private final BasePageParameter parameter;


    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.orderBy(RepositoryUtils.toOrders(root, criteriaBuilder).apply(parameter.getSorts()));
        return criteriaBuilder.and(
                List.of(
                                RepositoryUtils.toPredicate(root, criteriaBuilder).apply(parameter.getFilters()),
                                RepositoryUtils.toSearchPredicate(root, criteriaBuilder).apply(parameter.getKeyword(), parameter.getKeywordParams())
                        )
                        .toArray(Predicate[]::new)
        );
    }
}
