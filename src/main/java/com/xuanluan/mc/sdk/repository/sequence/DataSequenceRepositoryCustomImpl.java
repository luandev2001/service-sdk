package com.xuanluan.mc.sdk.repository.sequence;

import com.xuanluan.mc.sdk.domain.entity.DataSequence;
import com.xuanluan.mc.sdk.repository.BaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.LinkedList;
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
    public DataSequence findByClassName(String className, int type) {
        refresh();
        List<Predicate> filters = appendFilter("className", className, new LinkedList<>());
        appendFilter("type", type, filters);
        return getSingleResult(filters);
    }
}
