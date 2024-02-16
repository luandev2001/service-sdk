package com.xuanluan.mc.sdk.service.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.xuanluan.mc.sdk.exception.JpaConverterException;
import com.xuanluan.mc.sdk.utils.GeneratorUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Map;

@Converter
public class MapJpaConverter implements AttributeConverter<Map<String, Object>, String> {
    @Override
    public String convertToDatabaseColumn(Map<String, Object> stringObjectMap) {
        try {
            return GeneratorUtils.objectMapper.writeValueAsString(stringObjectMap);
        } catch (JsonProcessingException e) {
            throw new JpaConverterException(e, Map.class.getSimpleName(), String.class.getSimpleName());
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String s) {
        try {
            return GeneratorUtils.objectMapper.readValue(s, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new JpaConverterException(e, String.class.getSimpleName(), Map.class.getSimpleName());
        }
    }
}
