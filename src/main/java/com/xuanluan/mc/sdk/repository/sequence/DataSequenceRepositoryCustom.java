package com.xuanluan.mc.sdk.repository.sequence;

import com.xuanluan.mc.sdk.domain.entity.DataSequence;

/**
 * @author Xuan Luan
 * @createdAt 12/10/2022
 */
public interface DataSequenceRepositoryCustom {
    DataSequence findByClassName(String clientId, String orgId, String className, int type);
}
