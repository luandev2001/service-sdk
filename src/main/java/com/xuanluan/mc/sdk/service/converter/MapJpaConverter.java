package com.xuanluan.mc.sdk.service.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.xuanluan.mc.sdk.exception.MessageException;
import com.xuanluan.mc.sdk.rest.BaseRestClient;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.util.Map;

@Slf4j
public class MapJpaConverter implements AttributeConverter<Map<String, Object>, String> {
    @Override
    public String convertToDatabaseColumn(Map<String, Object> stringObjectMap) {
        try {
            return BaseRestClient.getObjectMapper().writeValueAsString(stringObjectMap);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw MessageException.assign("jpa.error.converter", "Map<String, Object>", "String");
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String s) {
        try {
            return BaseRestClient.getObjectMapper().readValue(s, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw MessageException.assign("jpa.error.converter", "String", "Map<String, Object>");
        }
    }
}
