package com.xuanluan.mc.sdk.service.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xuanluan.mc.sdk.exception.JpaConverterException;
import com.xuanluan.mc.sdk.utils.GeneratorUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ObjectJpaConverter implements AttributeConverter<Object, String> {

    @Override
    public String convertToDatabaseColumn(Object o) {
        try {
            return GeneratorUtils.getObjectMapper().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new JpaConverterException(e, Object.class.getSimpleName(), String.class.getSimpleName());
        }
    }

    @Override
    public Object convertToEntityAttribute(String s) {
        try {
            return GeneratorUtils.getObjectMapper().readValue(s, Object.class);
        } catch (JsonProcessingException e) {
            throw new JpaConverterException(e, String.class.getSimpleName(), Object.class.getSimpleName());
        }
    }
}
