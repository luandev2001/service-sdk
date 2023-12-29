package com.xuanluan.mc.sdk.service.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.xuanluan.mc.sdk.exception.ConverterException;
import com.xuanluan.mc.sdk.utils.GeneratorUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Map;

@Converter
public class MapJpaConverter implements AttributeConverter<Map<String, Object>, String> {
    @Override
    public String convertToDatabaseColumn(Map<String, Object> stringObjectMap) {
        try {
            return GeneratorUtils.getObjectMapper().writeValueAsString(stringObjectMap);
        } catch (JsonProcessingException e) {
            throw new ConverterException("jpa.error.converter");
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String s) {
        try {
            return GeneratorUtils.getObjectMapper().readValue(s, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new ConverterException("jpa.error.converter");
        }
    }
}
