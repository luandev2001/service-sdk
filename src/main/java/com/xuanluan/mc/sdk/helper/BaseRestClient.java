package com.xuanluan.mc.sdk.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xuanluan.mc.sdk.domain.model.WrapperResponse;
import com.xuanluan.mc.sdk.exception.UnprocessableException;
import com.xuanluan.mc.sdk.utils.GeneratorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

/**
 * @author Xuan Luan
 * @createdAt 1/4/2023
 */
@RequiredArgsConstructor
public abstract class BaseRestClient {
    private final String servicePath;
    private final String clientId;

    protected HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("clientId", clientId);
        return headers;
    }

    private <T> Object processRestClient(String path, HttpMethod method, HttpEntity<Object> entity, Class<T> tClass, boolean isWrapper) {
        try {
            if (isWrapper) {
                ResponseEntity<WrapperResponse<T>> response =
                        GeneratorUtils.restTemplate.exchange(
                                servicePath + path,
                                method,
                                entity,
                                ParameterizedTypeReference.forType(ResolvableType.forClassWithGenerics(WrapperResponse.class, tClass).getType())
                        );

                return response.getBody();
            } else {
                ResponseEntity<T> response =
                        GeneratorUtils.restTemplate.exchange(
                                servicePath + path,
                                method,
                                entity,
                                ParameterizedTypeReference.forType(tClass)
                        );

                return response.getBody();
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            try {
                WrapperResponse result = GeneratorUtils.objectMapper.readValue(e.getResponseBodyAsString(), WrapperResponse.class);
                throw new UnprocessableException(result.getMessage());
            } catch (JsonProcessingException jsonE) {
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
