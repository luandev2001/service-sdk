package com.xuanluan.mc.sdk.service.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.xuanluan.mc.sdk.rest.BaseRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

/**
 * @author Xuan Luan
 * @createdAt 12/29/2022
 */
public class BaseLoadFile {
    private static final Logger logger = LoggerFactory.getLogger(BaseLoadFile.class);

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
            String message = e.getMessage();
            try {
                inputStream.close();
            } catch (IOException ioException) {
                message = ioException.getMessage();
            }
            logger.error(message);
            return null;
        }
    }

    public static <T> T convertInputStream(String fileName, Class<T> tClass) {
        return convert(fileName, tClass, false);
    }

    public static <T> List<T> convertInputStreamToList(String fileName, Class<T> tClass) {
        return Collections.singletonList(convert(fileName, tClass, true));
    }
}
