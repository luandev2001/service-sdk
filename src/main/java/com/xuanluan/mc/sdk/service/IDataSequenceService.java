package com.xuanluan.mc.sdk.service;

import com.xuanluan.mc.sdk.domain.entity.DataSequence;
import com.xuanluan.mc.sdk.domain.enums.SequenceType;

public interface IDataSequenceService {
    <T> String getNextValue(Class<T> object, SequenceType type);

    <T> DataSequence increase(Class<T> object, SequenceType type);

    <T> DataSequence update(Class<T> object, SequenceType type);

    <T> DataSequence get(Class<T> object, SequenceType type);

    <T> DataSequence getConcurrent(Class<T> object, SequenceType type);
}
