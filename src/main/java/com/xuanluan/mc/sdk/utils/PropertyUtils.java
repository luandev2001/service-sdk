package com.xuanluan.mc.sdk.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Xuan Luan
 * @createdAt 1/5/2023
 */
@Slf4j
public class PropertyUtils {
    private static Properties properties;
    private final String env;
    private static PropertyUtils instance;

    private PropertyUtils(String env) {
        this.env = env;
    }

    public static void init(String currentEnv) {
        if (instance == null) {
            instance = new PropertyUtils(currentEnv);
        }
    }

    public static PropertyUtils getInstance() {
        AssertUtils.notNull(instance, "property");
        return instance;
    }

    public static String get(String name, String defaultValue) {
        String value = get(name);
        return StringUtils.hasTextAfterTrim(value) ? value : defaultValue;
    }

    public static String get(String name) {
        return getProperties().getProperty(name);
    }

    public static Properties getProperties(String propertiesFile) {
        Properties properties = new Properties();
        InputStream inputStream = PropertyUtils.class.getClassLoader().getResourceAsStream(propertiesFile);
        AssertUtils.notNull(inputStream, "input_stream");
        try {
            properties.load(inputStream);
        } catch (Exception e) {
            log.error("Error read file " + propertiesFile + ": " + e.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error("Error close resource: " + e.getMessage());
            }
        }
        return properties;
    }

    public static Properties getProperties() {
        AssertUtils.notBlank(getInstance().env, "environment");
        if (null == properties) {
            properties = getProperties("application-" + getInstance().env + ".properties");
        }
        return properties;
    }

}
