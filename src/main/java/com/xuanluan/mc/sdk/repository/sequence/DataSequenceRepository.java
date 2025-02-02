package com.xuanluan.mc.sdk.repository.sequence;

import com.xuanluan.mc.sdk.model.entity.DataSequence;
import com.xuanluan.mc.sdk.repository.JpaMultipleRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

/**
 * @author Xuan Luan
 * @createdAt 12/10/2022
 */
@Repository
@ConditionalOnProperty(name = "sdk.data_sequence.enabled", havingValue = "true")
public interface DataSequenceRepository extends JpaMultipleRepository<DataSequence, String> {
}
