package com.xuanluan.mc.sdk.repository;

import com.xuanluan.mc.sdk.model.request.jpa.QueryOptionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class BaseRepositoryV2<T, ID> extends SimpleJpaRepository<T, ID> implements IBaseJpaRepository<T> {
    protected EntityManager entityManager;

    public BaseRepositoryV2(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.entityManager = em;
    }


    @Override
    public List<T> findAll(Specification<T> spec, QueryOptionRequest request) {
        return getQueryByOptions(getQuery(spec, Sort.unsorted()), request).getResultList();
    }

    @Override
    public Optional<T> findOne(Specification<T> spec, QueryOptionRequest request) {
        try {
            return Optional.of(getQueryByOptions(getQuery(spec, Sort.unsorted()), request).getSingleResult());
        } catch (NoResultException var3) {
            return Optional.empty();
        }
    }

    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable, QueryOptionRequest request) {
        TypedQuery<T> query = getQueryByOptions(getQuery(spec, pageable), request);
        return (pageable.isUnpaged() ? new PageImpl<>(query.getResultList()) : this.readPage(query, this.getDomainClass(), pageable, spec));
    }

    protected TypedQuery<T> getQueryByOptions(TypedQuery<T> query, QueryOptionRequest request) {
        if (request.getGraphName() != null) {
            EntityGraph<?> entityGraph = entityManager.getEntityGraph(request.getGraphName());
            query.setHint(request.getGraphType().getKey(), entityGraph);
        }
        if (request.getLockMode() != null) {
            query.setLockMode(request.getLockMode());
        }
        return query;
    }
}
