package com.xuanluan.mc.repository.sequence;

import com.xuanluan.mc.domain.entity.DataSequence;
import com.xuanluan.mc.repository.BaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @author Xuan Luan
 * @createdAt 12/10/2022
 */
public class DataSequenceRepositoryCustomImpl extends BaseRepository<DataSequence>
        implements DataSequenceRepositoryCustom {

    protected DataSequenceRepositoryCustomImpl(EntityManager entityManager) {
        super(entityManager, DataSequence.class);
    }

    @Override
    public DataSequence findByClassName(String clientId, String orgId, String className, int type) {
        refresh();
        List<Predicate> filters = getFilters(clientId, orgId);
        appendFilter("className", className, filters);
        appendFilter("type", type, filters);
        return getSingleResult(filters);
    }
}
