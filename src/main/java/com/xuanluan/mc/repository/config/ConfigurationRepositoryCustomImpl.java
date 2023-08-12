package com.xuanluan.mc.repository.config;

import com.xuanluan.mc.domain.entity.Configuration;
import com.xuanluan.mc.domain.model.filter.ConfigurationFilter;
import com.xuanluan.mc.domain.model.filter.ResultList;
import com.xuanluan.mc.repository.BaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ConfigurationRepositoryCustomImpl extends BaseRepository<Configuration> implements ConfigurationRepositoryCustom {

    protected ConfigurationRepositoryCustomImpl(EntityManager entityManager) {
        super(entityManager, Configuration.class);
    }

    @Override
    public List<Configuration> findAll(String clientId) {
        return getListResult(getFilters(clientId));
    }

    @Override
    public List<Configuration> findAllByOrgIds(String clientId, Collection<String> orgIds) {
        return getListResult(appendFilter(orgIds, root.get("orgId").in(orgIds), getFilters(clientId)));
    }

    @Override
    public Configuration findByName(String clientId, String name) {
        return null;
    }

    @Override
    public ResultList<Configuration> search(String clientId, ConfigurationFilter filter) {
        List<Predicate> predicates = getFilterSearch(clientId, Set.of("name"), filter);
        appendFilter(filter.getTypes(), root.get("type").in(filter.getTypes()), predicates);
        appendFilter(filter.getTypes(), root.get("orgId").in(filter.getOrgIds()), predicates);
        return getResultList(predicates, filter.getIndex(), filter.getMaxResult());
    }
}
