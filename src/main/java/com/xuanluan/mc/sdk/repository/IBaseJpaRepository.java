package com.xuanluan.mc.sdk.repository;

import com.xuanluan.mc.sdk.model.request.page.BasePageParameter;
import com.xuanluan.mc.sdk.repository.specification.DynamicSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IBaseJpaRepository<T> extends JpaSpecificationExecutor<T> {
    default <P extends BasePageParameter> Page<T> getPage(P request) {
        Specification<T> dynamicFilter = DynamicSpecification.<T>builder()
                .filters(request.getFilters())
                .sorts(request.getSorts())
                .build();

        return findAll(dynamicFilter, PageRequest.of(request.getPage(), request.getSize()));
    }
}
