package com.xuanluan.mc.repository.config;

import com.xuanluan.mc.domain.entity.Configuration;
import com.xuanluan.mc.domain.model.filter.ConfigurationFilter;
import com.xuanluan.mc.domain.model.filter.ResultList;
import com.xuanluan.mc.repository.BaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Set;

public class ConfigurationRepositoryCustomImpl extends BaseRepository<Configuration> implements ConfigurationRepositoryCustom {

    protected ConfigurationRepositoryCustomImpl(EntityManager entityManager) {
        super(entityManager, Configuration.class);
    }

    @Override
    public List<Configuration> findAll(String clientId) {
        refresh();
        return getListResult(getFilters(clientId));
    }

    @Override
    public Configuration findByName(String clientId, String name) {
        refresh();
        return getSingleResult(appendFilter("name", name, getFilters(clientId)));
    }

    @Override
    public ResultList<Configuration> search(String clientId, ConfigurationFilter filter) {
        refresh();
        List<Predicate> predicates = getFilterSearch(clientId, Set.of("name"), filter);
        appendFilter(filter.getTypes(), root.get("type").in(filter.getTypes()), predicates);
        return getResultList(predicates, filter.getIndex(), filter.getMaxResult());
    }
}
