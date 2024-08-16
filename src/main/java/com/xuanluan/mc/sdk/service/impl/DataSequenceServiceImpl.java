package com.xuanluan.mc.sdk.service.impl;

import com.xuanluan.mc.sdk.domain.entity.DataSequence;
import com.xuanluan.mc.sdk.domain.enums.SequenceType;
import com.xuanluan.mc.sdk.exception.UnsupportedException;
import com.xuanluan.mc.sdk.repository.sequence.DataSequenceRepository;
import com.xuanluan.mc.sdk.service.IDataSequenceService;
import com.xuanluan.mc.sdk.utils.StringUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.function.Consumer;
import java.util.function.Function;

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

    @Value("${sequence.alphabet_dot_no.suffix.max:999999999}")
    private int maxSuffix;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public <T> DataSequence increase(Class<T> object, SequenceType type) {
        DataSequence sequenceConcurrent = get(object, type);
        sequenceConcurrent.setValue(generateValueNext(type, sequenceConcurrent.getValue()));
        return increase(object, type, _s -> {
        }).apply(0);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public <T> Function<Integer, DataSequence> increase(Class<T> object, SequenceType type, Consumer<String> consumer) {
        DataSequence dataSequence = get(object, type);
        if (dataSequence == null) dataSequence = init(object, type);

        DataSequence finalDataSequence = dataSequence;
        return number -> {
            Assert.isTrue(number >= 0, "require number greater than zero");
            while (number-- > 0) {
                finalDataSequence.setValue(generateValueNext(type, finalDataSequence.getValue()));
                consumer.accept(finalDataSequence.getValue());
            }
            return sequenceRepository.save(finalDataSequence);
        };
    }

    @Override
    public <T> DataSequence get(Class<T> object, SequenceType type) {
        Specification<DataSequence> specification = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("objectType"), object.getName()),
                criteriaBuilder.equal(root.get("type"), type)
        );
        return sequenceRepository.findOne(specification).orElse(null);
    }

    private <T> DataSequence init(Class<T> object, SequenceType type) {
        DataSequence dataSequence = new DataSequence();
        dataSequence.setType(type);
        dataSequence.setObjectType(object.getSimpleName());
        return dataSequence;
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
