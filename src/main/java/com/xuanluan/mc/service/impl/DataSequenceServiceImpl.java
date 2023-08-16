package com.xuanluan.mc.service.impl;

import com.xuanluan.mc.domain.entity.DataSequence;
import com.xuanluan.mc.domain.enums.SequenceType;
import com.xuanluan.mc.repository.sequence.DataSequenceRepository;
import com.xuanluan.mc.utils.BaseStringUtils;
import com.xuanluan.mc.utils.NumberUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Xuan Luan
 * @createdAt 12/10/2022
 */
@RequiredArgsConstructor
@Service
public class DataSequenceServiceImpl {
    private final Logger logger = LoggerFactory.getLogger(DataSequenceServiceImpl.class);
    private final DataSequenceRepository sequenceRepository;
    private final Map<String, DataSequence> sequenceMap = new HashMap<>();

    public <T> String getSequenceNext(String clientId, String orgId, Class<T> tClass, SequenceType type) {
        return getDataSequenceNext(clientId, orgId, tClass, type).getSequenceValue();
    }

    @Transactional(rollbackFor = Exception.class)
    public <T> DataSequence generateDataSequenceNext(String clientId, String orgId, Class<T> tClass, SequenceType type) {
        //increase sequence value
        DataSequence sequence = getDataSequenceNext(clientId, orgId, tClass, type);
        logger.info("Cập nhật sequence, type= " + sequence.getType() + ", thành " + sequence.getSequenceValue());
        return sequenceRepository.save(sequence);
    }

    @Transactional(rollbackFor = Exception.class)
    public <T> DataSequence generateDataSequence(String clientId, String orgId, Class<T> tClass, SequenceType type) {
        DataSequence sequence = getSequence(clientId, orgId, tClass, type);
        if (!BaseStringUtils.hasTextAfterTrim(sequence.getSequenceValue())) {
            sequence = generateDataSequenceNext(clientId, orgId, tClass, type);
        }
        logger.info("Cập nhật sequence, type= " + sequence.getType() + ", thành " + sequence.getSequenceValue());
        return sequenceRepository.save(sequence);
    }

    private <T> DataSequence getDataSequenceNext(String clientId, String orgId, Class<T> tClass, SequenceType type) {
        DataSequence currentSequence = getSequence(clientId, orgId, tClass, type);
        currentSequence = generateDataSequence(clientId, orgId, tClass, type, currentSequence);
        generateSequenceValue(currentSequence);
        return currentSequence;
    }

    private <T> DataSequence getSequence(String clientId, String orgId, Class<T> tClass, SequenceType type) {
        Assert.isTrue(BaseStringUtils.hasTextAfterTrim(clientId), "clientId must be not null");
        Assert.isTrue(BaseStringUtils.hasTextAfterTrim(orgId), "orgId must be not null");
        Assert.notNull(tClass, "class must be not null");
        Assert.notNull(type, "type must be not null");

        String key = clientId + "_" + orgId + "_" + tClass.getName() + "_" + type.name();
        DataSequence currentSequence = sequenceMap.get(key);
        if (currentSequence == null) {
            currentSequence = getDataSequence(clientId, orgId, tClass.getName(), type);
            currentSequence = currentSequence != null ? currentSequence : generateDataSequence(clientId, orgId, tClass, type, null);
            sequenceMap.put(key, currentSequence);
        }
        return currentSequence;
    }

    private <T> DataSequence generateDataSequence(String clientId, String orgId, Class<T> tClass, SequenceType type, DataSequence sequence) {
        if (sequence == null) {
            sequence = new DataSequence();
            sequence.setClientId(clientId);
            sequence.setClassName(tClass.getName());
            sequence.setOrgId(orgId);
            sequence.setType(type.value);
            sequence.setCreatedBy("system");
        } else {
            sequence.setUpdatedAt(new Date());
            sequence.setUpdatedBy("system");
        }
        return sequence;
    }

    private DataSequence getDataSequence(String clientId, String orgId, String className, SequenceType type) {
        return sequenceRepository.findByClassName(clientId, orgId, className, type.value);
    }

    private void generateSequenceValue(DataSequence sequence) {
        if (SequenceType.ALPHABET_DOT_NO.value == sequence.getType()) {
            sequence.setSequenceValue(BaseStringUtils.generateAlphabetDotNoCode(sequence.getSequenceValue()));
        } else {
            String oldSeq = sequence.getSequenceValue() != null ? sequence.getSequenceValue() : "0";
            long oldSeqNumber = Long.parseLong(oldSeq) + 1;
            sequence.setSequenceValue("" + oldSeqNumber);
        }
    }
}
