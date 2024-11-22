package com.xuanluan.mc.sdk.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.xuanluan.mc.sdk.utils.GeneratorUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author Xuan Luan
 * @createdAt 12/29/2022
 */
public class BaseLoadFile {
    public static InputStream loadDataFromFile(String fileName) {
        return BaseLoadFile.class.getClassLoader().getResourceAsStream(fileName);
    }

    private static <T> T convert(String fileName, Class<T> tClass, boolean isList) {
        try (InputStreamReader reader = new InputStreamReader(loadDataFromFile(fileName))) {
            ObjectMapper mapper = GeneratorUtils.objectMapper;
            if (isList) {
                CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, tClass);
                return mapper.readValue(reader, listType);
            }
            return mapper.readValue(reader, tClass);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static <T> T convertInputStream(String fileName, Class<T> tClass) {
        return convert(fileName, tClass, false);
    }

    public static <T> List<T> convertInputStreamToList(String fileName, Class<T> tClass) {
        return (List<T>) convert(fileName, tClass, true);
    }
}
