package com.xuanluan.mc.sdk.service.impl;

import com.xuanluan.mc.sdk.domain.entity.DataSequence;
import com.xuanluan.mc.sdk.domain.enums.SequenceType;
import com.xuanluan.mc.sdk.exception.UnsupportedException;
import com.xuanluan.mc.sdk.repository.sequence.DataSequenceRepository;
import com.xuanluan.mc.sdk.service.IDataSequenceService;
import com.xuanluan.mc.sdk.service.i18n.MessageAssert;
import com.xuanluan.mc.sdk.utils.StringUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * avoid use with async
 *
 * @author Xuan Luan
 * @createdAt 12/10/2022
 */
@RequiredArgsConstructor
@Service
public class DataSequenceServiceImpl implements IDataSequenceService {
    private final DataSequenceRepository sequenceRepository;
    private final Map<String, DataSequence> sequenceMap = new ConcurrentHashMap<>();
    private final MessageAssert messageAssert;

    @Value("${sequence.alphabet_dot_no.suffix.max:999999999}")
    private int maxSuffix;

    @Override
    public <T> String getNextValue(Class<T> object, SequenceType type) {
        DataSequence sequenceConcurrent = getConcurrent(object, type);
        sequenceConcurrent.setValue(generateValueNext(type, sequenceConcurrent.getValue()));
        return sequenceConcurrent.getValue();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public <T> DataSequence increase(Class<T> object, SequenceType type) {
        getNextValue(object, type);
        return update(object, type);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public <T> DataSequence update(Class<T> object, SequenceType type) {
        return sequenceRepository.save(getConcurrent(object, type));
    }

    @Override
    public <T> DataSequence get(Class<T> object, SequenceType type) {
        Specification<DataSequence> specification = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("objectType"), object.getName()),
                criteriaBuilder.equal(root.get("type"), type)
        );
        return sequenceRepository.findOne(specification).orElse(null);
    }

    public <T> DataSequence getConcurrent(Class<T> object, SequenceType type) {
        messageAssert.notNull(object, "object");
        messageAssert.notNull(type, "type");

        String key = String.join(":", object.getName(), type.name());
        return sequenceMap.computeIfAbsent(key,
                (_key) -> {
                    DataSequence dataSequence = get(object, type);
                    return dataSequence != null ? dataSequence : create(object, type);
                }
        );
    }

    private <T> DataSequence create(Class<T> object, SequenceType type) {
        DataSequence dataSequence = new DataSequence();
        dataSequence.setType(type);
        dataSequence.setValue(generateValueNext(dataSequence.getType(), null));
        dataSequence.setObjectType(object.getSimpleName());
        return sequenceRepository.save(dataSequence);
    }

    private String generateValueNext(SequenceType type, String value) {
        switch (type) {
            case ALPHABET_DOT_NO:
                return StringUtils.generateAlphabetDotNoCode(value, maxSuffix);
            case NUMBER:
                String oldSeq = StringUtils.hasText(value) ? value : "0";
                long oldSeqNumber = Long.parseLong(oldSeq) + 1;
                return "" + oldSeqNumber;
            default:
                throw new UnsupportedException();
        }
    }
}
