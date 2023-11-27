package com.xuanluan.mc.sdk.service.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.xuanluan.mc.sdk.rest.BaseRestClient;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

/**
 * @author Xuan Luan
 * @createdAt 12/29/2022
 */
@Slf4j
public class BaseLoadFile {

    protected static InputStream loadDataFromFile(String fileName) {
        return BaseLoadFile.class.getClassLoader().getResourceAsStream(fileName);
    }

    private static <T> T convert(String fileName, Class<T> tClass, boolean isList) {
        InputStream inputStream = loadDataFromFile(fileName);
        try {
            ObjectMapper mapper = BaseRestClient.getObjectMapper();
            if (isList) {
                CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, tClass);
                return mapper.readValue(new InputStreamReader(inputStream), listType);
            }
            return mapper.readValue(new InputStreamReader(inputStream), tClass);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    public static <T> T convertInputStream(String fileName, Class<T> tClass) {
        return convert(fileName, tClass, false);
    }

    public static <T> List<T> convertInputStreamToList(String fileName, Class<T> tClass) {
        return Collections.singletonList(convert(fileName, tClass, true));
    }
}
