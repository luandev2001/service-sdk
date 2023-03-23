package com.xuanluan.mc.service.impl;

import com.xuanluan.mc.domain.entity.DataSequence;
import com.xuanluan.mc.domain.enums.SequenceType;
import com.xuanluan.mc.repository.sequence.DataSequenceRepository;
import com.xuanluan.mc.service.BaseService;
import com.xuanluan.mc.utils.BaseStringUtils;
import com.xuanluan.mc.utils.NumberUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * @author Xuan Luan
 * @createdAt 12/10/2022
 */
@RequiredArgsConstructor
@Service
public class DataSequenceServiceImpl {
    private final Logger logger = LoggerFactory.getLogger(DataSequenceServiceImpl.class);
    private final DataSequenceRepository sequenceRepository;

    public DataSequence getDataSequence(String clientId, String orgId, String className, SequenceType type) {
        Assert.isTrue(BaseStringUtils.hasTextAfterTrim(clientId), "clientId must be not null");
        return sequenceRepository.findByClassName(clientId, orgId, className, type.value);
    }

    @Transactional(rollbackFor = Exception.class)
    public <T> DataSequence generateDataSequence(String clientId, String orgId, Class<T> tClass, SequenceType type) {
        String className = tClass.getName();
        DataSequence sequence = getDataSequence(clientId, orgId, className, type);
        if (sequence == null) {
            sequence = new DataSequence();
            sequence.setClientId(clientId);
            sequence.setClassName(className);
            sequence.setOrgId(orgId);
            sequence.setType(type.value);
            sequence.setCreatedBy("Hệ thống");
        } else {
            sequence.setUpdatedAt(new Date());
            sequence.setUpdatedBy("Hệ thống");
        }
        generateSequenceValue(sequence);
        logger.info("Cập nhật sequence, type= " + sequence.getType() + ", thành " + sequence.getSequenceValue());
        return sequenceRepository.save(sequence);
    }

    private void generateSequenceValue(DataSequence sequence) {
        if (SequenceType.ALPHABET_DOT_NO.value == sequence.getType()) {
            sequence.setSequenceValue(BaseService.generateAlphabetDotNoCode(sequence.getSequenceValue()));
        } else {
            String oldSeq = sequence.getSequenceValue() != null ? sequence.getSequenceValue() : "0";
            long oldSeqNumber = NumberUtils.convertTextToNumber(oldSeq) + 1;
            sequence.setSequenceValue("" + oldSeqNumber);
        }
    }
}
