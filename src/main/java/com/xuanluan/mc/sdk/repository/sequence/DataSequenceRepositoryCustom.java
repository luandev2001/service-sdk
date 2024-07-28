package com.xuanluan.mc.sdk.repository.sequence;

import com.xuanluan.mc.sdk.domain.entity.DataSequence;
import com.xuanluan.mc.sdk.domain.enums.SequenceType;

/**
 * @author Xuan Luan
 * @createdAt 12/10/2022
 */
public interface DataSequenceRepositoryCustom {
    DataSequence findByClassName(String className, SequenceType type);
}
