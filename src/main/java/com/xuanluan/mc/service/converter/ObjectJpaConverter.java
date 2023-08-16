package com.xuanluan.mc.service.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xuanluan.mc.exception.MessageException;
import com.xuanluan.mc.rest.BaseRestClient;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;

@Slf4j
public class ObjectJpaConverter implements AttributeConverter<Object, String> {

    @Override
    public String convertToDatabaseColumn(Object o) {
        try {
            return BaseRestClient.getObjectMapper().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw MessageException.assign("jpa.error.converter", "Object", "String");
        }
    }

    @Override
    public Object convertToEntityAttribute(String s) {
        try {
            return BaseRestClient.getObjectMapper().readValue(s, Object.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw MessageException.assign("jpa.error.converter", "String", "Object");
        }
    }
}
