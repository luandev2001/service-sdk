package com.xuanluan.mc.repository.sequence;

import com.xuanluan.mc.domain.entity.DataSequence;

/**
 * @author Xuan Luan
 * @createdAt 12/10/2022
 */
public interface DataSequenceRepositoryCustom {
    DataSequence findByClassName(String clientId, String orgId, String className, int type);
}
