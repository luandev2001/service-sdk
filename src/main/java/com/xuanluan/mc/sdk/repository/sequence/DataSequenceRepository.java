package com.xuanluan.mc.sdk.repository.sequence;

import com.xuanluan.mc.sdk.domain.entity.DataSequence;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Xuan Luan
 * @createdAt 12/10/2022
 */
@Repository
public interface DataSequenceRepository extends CrudRepository<DataSequence, String>, DataSequenceRepositoryCustom {
}
