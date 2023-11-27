package com.xuanluan.mc.sdk.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuanluan.mc.sdk.domain.model.WrapperResponse;
import com.xuanluan.mc.sdk.exception.MessageException;
import com.xuanluan.mc.sdk.utils.AssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * @author Xuan Luan
 * @createdAt 1/4/2023
 */
public abstract class BaseRestClient {
    private final Logger logger = LoggerFactory.getLogger(BaseRestClient.class);

    protected final String servicePath;
    protected final String clientId;
    private static RestTemplate restTemplate;
    private static ObjectMapper objectMapper;

    protected BaseRestClient(String servicePath, String clientId) {
        this.servicePath = servicePath;
        this.clientId = clientId;
        AssertUtils.notBlank(this.servicePath, "service_path");
        AssertUtils.notBlank(this.clientId, "client");
    }

    protected HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("clientId", clientId);
        return headers;
    }

    public static RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }
        return restTemplate;
    }

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

    private <T> Object processRestClient(String path, HttpMethod method, HttpEntity<Object> entity, Class<T> tClass, boolean isWrapper) {
        try {
            if (isWrapper) {
                ResponseEntity<WrapperResponse<T>> response =
                        getRestTemplate().exchange(
                                servicePath + path,
                                method,
                                entity,
                                ParameterizedTypeReference.forType(ResolvableType.forClassWithGenerics(WrapperResponse.class, tClass).getType())
                        );

                return response.getBody();
            } else {
                ResponseEntity<T> response =
                        getRestTemplate().exchange(
                                servicePath + path,
                                method,
                                entity,
                                ParameterizedTypeReference.forType(tClass)
                        );

                return response.getBody();
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            try {
                WrapperResponse result = getObjectMapper().readValue(e.getResponseBodyAsString(), WrapperResponse.class);
                throw new MessageException(result.getMessage_vn(), result.getMessage(), result.getStatus());
            } catch (JsonProcessingException jsonE) {
                logger.error(jsonE.getMessage(), jsonE);
                throw new RuntimeException(jsonE);
            }
        }
    }

    private <T> T processRestClient(String path, HttpMethod method, HttpEntity<Object> entity, Class<T> tClass) {
        return (T) processRestClient(path, method, entity, tClass, false);
    }

    private <T> WrapperResponse<T> processWrapperRestClient(String path, HttpMethod method, HttpEntity<Object> entity, Class<T> tClass) {
        return (WrapperResponse<T>) processRestClient(path, method, entity, tClass, true);
    }

    protected <T> T get(String path, Class<T> tClass) {
        HttpEntity<Object> entity = new HttpEntity<>(getHeaders());
        return processRestClient(path, HttpMethod.GET, entity, tClass);
    }

    protected <T> T get(String path, Class<T> tClass, HttpHeaders headers) {
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        return processRestClient(path, HttpMethod.GET, entity, tClass);
    }

    protected <T> T post(String path, Object request, Class<T> tClass) {
        HttpEntity<Object> entity = new HttpEntity<>(request, getHeaders());
        return processRestClient(path, HttpMethod.POST, entity, tClass);
    }

    protected <T> T post(String path, Object request, Class<T> tClass, HttpHeaders headers) {
        HttpEntity<Object> entity = new HttpEntity<>(request, headers);
        return processRestClient(path, HttpMethod.POST, entity, tClass);
    }

    protected <T> T put(String path, Object request, Class<T> tClass) {
        HttpEntity<Object> entity = new HttpEntity<>(request, getHeaders());
        return processRestClient(path, HttpMethod.PUT, entity, tClass);
    }

    protected <T> T put(String path, Object request, Class<T> tClass, HttpHeaders headers) {
        HttpEntity<Object> entity = new HttpEntity<>(request, headers);
        return processRestClient(path, HttpMethod.PUT, entity, tClass);
    }

    protected <T> T delete(String path, Object request, Class<T> tClass) {
        HttpEntity<Object> entity = new HttpEntity<>(request, getHeaders());
        return processRestClient(path, HttpMethod.DELETE, entity, tClass);
    }

    protected <T> T delete(String path, Object request, Class<T> tClass, HttpHeaders headers) {
        HttpEntity<Object> entity = new HttpEntity<>(request, headers);
        return processRestClient(path, HttpMethod.DELETE, entity, tClass);
    }

    //******************* WrapperResponse *******************
    protected <T> WrapperResponse<T> getWrapper(String path, Class<T> tClass) {
        HttpEntity<Object> entity = new HttpEntity<>(getHeaders());
        return processWrapperRestClient(path, HttpMethod.GET, entity, tClass);
    }

    protected <T> WrapperResponse<T> getWrapper(String path, Class<T> tClass, HttpHeaders headers) {
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        return processWrapperRestClient(path, HttpMethod.GET, entity, tClass);
    }

    protected <T> WrapperResponse<T> postWrapper(String path, Object request, Class<T> tClass) {
        HttpEntity<Object> entity = new HttpEntity<>(request, getHeaders());
        return processWrapperRestClient(path, HttpMethod.POST, entity, tClass);
    }

    protected <T> WrapperResponse<T> postWrapper(String path, Object request, Class<T> tClass, HttpHeaders headers) {
        HttpEntity<Object> entity = new HttpEntity<>(request, headers);
        return processWrapperRestClient(path, HttpMethod.POST, entity, tClass);
    }

    protected <T> WrapperResponse<T> putWrapper(String path, Object request, Class<T> tClass) {
        HttpEntity<Object> entity = new HttpEntity<>(request, getHeaders());
        return processWrapperRestClient(path, HttpMethod.PUT, entity, tClass);
    }

    protected <T> WrapperResponse<T> putWrapper(String path, Object request, Class<T> tClass, HttpHeaders headers) {
        HttpEntity<Object> entity = new HttpEntity<>(request, headers);
        return processWrapperRestClient(path, HttpMethod.PUT, entity, tClass);
    }

    protected <T> WrapperResponse<T> deleteWrapper(String path, Object request, Class<T> tClass) {
        HttpEntity<Object> entity = new HttpEntity<>(request, getHeaders());
        return processWrapperRestClient(path, HttpMethod.DELETE, entity, tClass);
    }

    protected <T> WrapperResponse<T> deleteWrapper(String path, Object request, Class<T> tClass, HttpHeaders headers) {
        HttpEntity<Object> entity = new HttpEntity<>(request, headers);
        return processWrapperRestClient(path, HttpMethod.DELETE, entity, tClass);
    }
}
