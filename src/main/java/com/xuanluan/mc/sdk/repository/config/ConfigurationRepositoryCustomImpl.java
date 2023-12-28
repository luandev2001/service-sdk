package com.xuanluan.mc.sdk.repository.config;

import com.xuanluan.mc.sdk.domain.entity.Configuration;
import com.xuanluan.mc.sdk.domain.model.filter.ConfigurationFilter;
import com.xuanluan.mc.sdk.domain.model.filter.ResultList;
import com.xuanluan.mc.sdk.repository.BaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ConfigurationRepositoryCustomImpl extends BaseRepository<Configuration> implements ConfigurationRepositoryCustom {

    protected ConfigurationRepositoryCustomImpl(EntityManager entityManager) {
        super(entityManager, Configuration.class);
    }

    @Override
    public ResultList<Configuration> search(ConfigurationFilter filter) {
        refresh();
        List<Predicate> predicates = filterSearch(Set.of("name"), filter);
        appendFilter(root.get("type").in(filter.getTypes()), filter.getTypes(), predicates);
        appendFilter(root.get("dataType").in(filter.getDataTypes()), filter.getDataTypes(), predicates);
        return getResultList(predicates, filter.getIndex(), filter.getMaxResult());
    }
}
