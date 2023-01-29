package com.xuanluan.mc.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuanluan.mc.domain.model.WrapperResponse;
import com.xuanluan.mc.exception.ServiceException;
import com.xuanluan.mc.utils.BaseStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * @author Xuan Luan
 * @createdAt 1/4/2023
 */
public abstract class BaseRestClient {
    private final Logger logger = LoggerFactory.getLogger(BaseRestClient.class);

    private final String servicePath;
    private final String clientId;
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    protected BaseRestClient(String servicePath, String clientId) {
        this.servicePath = servicePath;
        this.clientId = clientId;
        Assert.isTrue(BaseStringUtils.hasTextAfterTrim(this.servicePath), "servicePath must be not null");
        Assert.notNull(this.clientId, "clientId must be not null");
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("clientId", clientId);

        return headers;
    }

    private HttpHeaders getHeaders(String token) {
        HttpHeaders headers = getHeaders();
        headers.set("X-CSRFToken", token);
        return headers;
    }

    protected final RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }
        return restTemplate;
    }

    protected final ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

    private <T> T processRestClient(String path, HttpMethod method, HttpEntity<Object> entity, Class<T> tClass) {
        try {
            ParameterizedTypeReference<T> typeRef = ParameterizedTypeReference.forType(tClass);
            ResponseEntity<T> response =
                    getRestTemplate().exchange(
                            servicePath + path,
                            method,
                            entity,
                            typeRef);

            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            try {
                WrapperResponse result = getObjectMapper().readValue(e.getResponseBodyAsString(), WrapperResponse.class);
                throw new ServiceException(result.getStatus(), result.getMessage(), result.getData());
            } catch (JsonProcessingException jsonE) {
                logger.error(jsonE.getMessage(), jsonE);
                throw new ServiceException(e.getStatusCode(), jsonE.getOriginalMessage(), "Đã xảy ra lỗi :" + jsonE.getMessage());
            }
        }
    }

    protected <T> T get(String path, Class<T> tClass) {
        HttpEntity<Object> entity = new HttpEntity<>(getHeaders());
        return processRestClient(path, HttpMethod.GET, entity, tClass);
    }

    protected <T> T get(String path, String token, Class<T> tClass) {
        HttpEntity<Object> entity = new HttpEntity<>(getHeaders(token));
        return processRestClient(path, HttpMethod.GET, entity, tClass);
    }

    protected <T> T post(String path, Object request, Class<T> tClass) {
        HttpEntity<Object> entity = new HttpEntity<>(request, getHeaders());
        return processRestClient(path, HttpMethod.POST, entity, tClass);
    }

    protected <T> T post(String path, Object request, String token, Class<T> tClass) {
        HttpEntity<Object> entity = new HttpEntity<>(request, getHeaders(token));
        return processRestClient(path, HttpMethod.POST, entity, tClass);
    }

    protected <T> T put(String path, Object request, Class<T> tClass) {
        HttpEntity<Object> entity = new HttpEntity<>(request, getHeaders());
        return processRestClient(path, HttpMethod.PUT, entity, tClass);
    }

    protected <T> T put(String path, Object request, String token, Class<T> tClass) {
        HttpEntity<Object> entity = new HttpEntity<>(request, getHeaders(token));
        return processRestClient(path, HttpMethod.PUT, entity, tClass);
    }

    protected <T> T delete(String path, Object request, Class<T> tClass) {
        HttpEntity<Object> entity = new HttpEntity<>(request, getHeaders());
        return processRestClient(path, HttpMethod.DELETE, entity, tClass);
    }

    protected <T> T delete(String path, Object request, String token, Class<T> tClass) {
        HttpEntity<Object> entity = new HttpEntity<>(request, getHeaders(token));
        return processRestClient(path, HttpMethod.DELETE, entity, tClass);
    }
}
