package com.xuanluan.mc.sdk.service.impl;

import com.xuanluan.mc.sdk.domain.entity.DataSequence;
import com.xuanluan.mc.sdk.domain.enums.SequenceType;
import com.xuanluan.mc.sdk.repository.sequence.DataSequenceRepository;
import com.xuanluan.mc.sdk.service.i18n.MessageAssert;
import com.xuanluan.mc.sdk.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * avoid use with async
 *
 * @author Xuan Luan
 * @createdAt 12/10/2022
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DataSequenceServiceImpl {
    private final DataSequenceRepository sequenceRepository;
    private final Map<String, DataSequence> sequenceMap = new HashMap<>();
    private final MessageAssert messageAssert;
    @Value("${sequence.alphabet_dot_no.suffix.max:999999999}")
    private int maxSuffix;

    public <T> String getSequenceNext(Class<T> tClass, SequenceType type) {
        return getDataSequenceNext(tClass, type).getSequenceValue();
    }

    @Transactional(rollbackFor = Exception.class)
    public <T> DataSequence generateDataSequenceNext(Class<T> tClass, SequenceType type) {
        //increase sequence value
        DataSequence sequence = getDataSequenceNext(tClass, type);
        log.info("Cập nhật sequence, type= {}, thành {}", sequence.getType(), sequence.getSequenceValue());
        return sequenceRepository.save(sequence);
    }

    @Transactional(rollbackFor = Exception.class)
    public <T> DataSequence generateDataSequence(Class<T> tClass, SequenceType type) {
        DataSequence sequence = getSequence(tClass, type);
        if (!StringUtils.hasText(sequence.getSequenceValue())) {
            sequence = generateDataSequenceNext(tClass, type);
        }
        return sequenceRepository.save(sequence);
    }

    private <T> DataSequence getDataSequenceNext(Class<T> tClass, SequenceType type) {
        DataSequence currentSequence = getSequence(tClass, type);
        generateDataSequence(tClass, type, currentSequence);
        generateSequenceValue(currentSequence);
        return currentSequence;
    }

    private <T> DataSequence getSequence(Class<T> tClass, SequenceType type) {
        messageAssert.notNull(tClass, "class");
        messageAssert.notNull(type, "type");

        String key = tClass.getName() + "_" + type.name();
        DataSequence currentSequence = sequenceMap.get(key);
        if (currentSequence == null) {
            currentSequence = getDataSequence(tClass.getName(), type);
            currentSequence = currentSequence != null ? currentSequence : generateDataSequence(tClass, type, null);
            sequenceMap.put(key, currentSequence);
        }
        return currentSequence;
    }

    private <T> DataSequence generateDataSequence(Class<T> tClass, SequenceType type, DataSequence sequence) {
        if (sequence == null) {
            sequence = new DataSequence();
            sequence.setClassName(tClass.getName());
            sequence.setType(type);
        }
        return sequence;
    }

    private DataSequence getDataSequence(String className, SequenceType type) {
        return sequenceRepository.findByClassName(className, type);
    }

    private void generateSequenceValue(DataSequence sequence) {
        if (SequenceType.ALPHABET_DOT_NO == sequence.getType()) {
            sequence.setSequenceValue(StringUtils.generateAlphabetDotNoCode(sequence.getSequenceValue(), maxSuffix));
        } else {
            String oldSeq = sequence.getSequenceValue() != null ? sequence.getSequenceValue() : "0";
            long oldSeqNumber = Long.parseLong(oldSeq) + 1;
            sequence.setSequenceValue("" + oldSeqNumber);
        }
    }
}
