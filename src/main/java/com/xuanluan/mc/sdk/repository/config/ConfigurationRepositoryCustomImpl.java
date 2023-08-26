package com.xuanluan.mc.sdk.repository.config;

import com.xuanluan.mc.sdk.domain.entity.Configuration;
import com.xuanluan.mc.sdk.domain.model.filter.ConfigurationFilter;
import com.xuanluan.mc.sdk.domain.model.filter.ResultList;
import com.xuanluan.mc.sdk.repository.BaseRepository;

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
    public List<Configuration> findAll(String clientId, String type) {
        refresh();
        return getListResult(appendFilter("type", type, getFilters(clientId)));
    }

    @Override
    public Configuration findByName(String clientId, String name, String type) {
        refresh();
        return getSingleResult(appendFilter("type", type, appendFilter("name", name, getFilters(clientId))));
    }

    @Override
    public ResultList<Configuration> search(String clientId, ConfigurationFilter filter) {
        refresh();
        List<Predicate> predicates = getFilterSearch(clientId, Set.of("name"), filter);
        appendFilter(filter.getTypes(), root.get("type").in(filter.getTypes()), predicates);
        return getResultList(predicates, filter.getIndex(), filter.getMaxResult());
    }
}
