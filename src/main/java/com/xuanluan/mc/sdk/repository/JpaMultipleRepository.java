package com.xuanluan.mc.sdk.repository;

import com.xuanluan.mc.sdk.model.request.jpa.QueryOptionRequest;
import com.xuanluan.mc.sdk.model.request.page.BasePageParameter;
import com.xuanluan.mc.sdk.repository.specification.DynamicSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface JpaMultipleRepository<T, ID>
        extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>, IBaseJpaRepository<T> {

    default <P extends BasePageParameter> Page<T> getPage(P request) {
        Specification<T> dynamicFilter = new DynamicSpecification<>(request);
        return findAll(dynamicFilter, PageRequest.of(request.getPage(), request.getSize()));
    }

    default <P extends BasePageParameter> Page<T> getPage(P request, QueryOptionRequest optionRequest) {
        Specification<T> dynamicFilter = new DynamicSpecification<>(request);
        return findAll(dynamicFilter, PageRequest.of(request.getPage(), request.getSize()), optionRequest);
    }
}
