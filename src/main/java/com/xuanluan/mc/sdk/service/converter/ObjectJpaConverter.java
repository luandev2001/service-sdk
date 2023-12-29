package com.xuanluan.mc.sdk.service.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xuanluan.mc.sdk.exception.ConverterException;
import com.xuanluan.mc.sdk.rest.BaseRestClient;
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
            throw new ConverterException("jpa.error.converter");
        }
    }

    @Override
    public Object convertToEntityAttribute(String s) {
        try {
            return GeneratorUtils.getObjectMapper().readValue(s, Object.class);
        } catch (JsonProcessingException e) {
            throw new ConverterException("jpa.error.converter");
        }
    }
}
