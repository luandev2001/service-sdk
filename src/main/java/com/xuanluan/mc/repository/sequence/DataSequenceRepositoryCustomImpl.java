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
        List<Predicate> filters = getFilters(clientId);
        filters.add(filterEqualAnyField("orgId", className));
        filters.add(filterEqualAnyField("className", className));
        filters.add(filterEqualAnyField("type", type));
        return getSingleResult(filters);
    }
}
