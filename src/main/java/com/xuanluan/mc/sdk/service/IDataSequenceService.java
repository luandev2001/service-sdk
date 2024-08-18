package com.xuanluan.mc.sdk.service;

import com.xuanluan.mc.sdk.model.entity.DataSequence;
import com.xuanluan.mc.sdk.model.enums.SequenceType;

import java.util.function.Consumer;
import java.util.function.Function;

public interface IDataSequenceService {
    <T> DataSequence increase(Class<T> object, SequenceType type);

    <T> Function<Integer, DataSequence> increase(Class<T> object, SequenceType type, Consumer<String> consumer);

    <T> DataSequence get(Class<T> object, SequenceType type);
}
