package com.xuanluan.mc.sdk.repository.config;

import com.xuanluan.mc.sdk.domain.entity.Configuration;
import com.xuanluan.mc.sdk.domain.model.filter.ConfigurationFilter;
import com.xuanluan.mc.sdk.repository.BaseRepository;
import org.springframework.data.domain.Page;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Set;

public class ConfigurationRepositoryCustomImpl extends BaseRepository<Configuration> implements ConfigurationRepositoryCustom {

    protected ConfigurationRepositoryCustomImpl(EntityManager entityManager) {
        super(entityManager, Configuration.class);
    }

    @Override
    public Page<Configuration> search(ConfigurationFilter filter) {
        refresh();
        List<Predicate> predicates = filterSearch(Set.of("name"), filter);
        appendFilter(filter.getTypes(), predicates).apply(root.get("type").in(filter.getTypes()));
        appendFilter(filter.getDataTypes(), predicates).apply(root.get("dataType").in(filter.getDataTypes()));
        return getResultList(predicates, filter.getPage(), filter.getSize());
    }
}
