package com.xuanluan.mc.sdk.repository.sequence;

import com.xuanluan.mc.sdk.domain.entity.DataSequence;
import com.xuanluan.mc.sdk.repository.JpaSpecificationRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Xuan Luan
 * @createdAt 12/10/2022
 */
@Repository
public interface DataSequenceRepository extends JpaSpecificationRepository<DataSequence, String>, DataSequenceRepositoryCustom {
}
