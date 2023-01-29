package com.xuanluan.mc.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Xuan Luan
 * @createdAt 12/29/2022
 */
public class BaseLoadFile {
    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    protected InputStream loadDataFromFile(String fileName) {
        return BaseLoadFile.class.getClassLoader().getResourceAsStream(fileName);
    }

    protected <T> Object convertInputStreamToList(String fileName, Class<T> tClass) {
        InputStream inputStream = loadDataFromFile(fileName);
        if (inputStream == null) {
            logger.error("Not found file config: " + fileName);
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new InputStreamReader(inputStream), tClass);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            try {
                inputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return null;
    }
}
