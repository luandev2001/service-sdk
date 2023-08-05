package com.xuanluan.mc.utils;

import com.xuanluan.mc.exception.ServiceException;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Xuan Luan
 * @createdAt 1/5/2023
 */
public class PropertyReaderUtils {
    private static Properties applicationProperties;
    private static Properties profileProperties;

    public static String getPropertyName(String name, String defaultValue) {
        String content = getProfileProperties().getProperty(name);
        if (null == content || content.isEmpty()) {
            content = System.getProperty(name);
            if (null == content || content.isEmpty()) {
                content = defaultValue;
            }
        }
        return content;
    }

    public static String getPropertyName(String name) {
        String content = getProfileProperties().getProperty(name);
        if (null == content || content.isEmpty()) {
            content = System.getProperty(name);
        }
        return content;
    }

    private static Properties getProperties(String propertiesFile) {
        Properties properties = new Properties();
        InputStream inputStream = PropertyReaderUtils.class.getClassLoader().getResourceAsStream(propertiesFile);
        try {
            properties.load(inputStream);
        } catch (Exception e) {
            Logger logger = Logger.getLogger(PropertyReaderUtils.class.getSimpleName());
            logger.log(Level.INFO, "Error read properties " + propertiesFile + ": " + e.getMessage());

            try {
                assert inputStream != null;
                inputStream.close();
            } catch (IOException ioException) {
                throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ioException.getMessage(), "Đã xảy ra lỗi: " + ioException.getMessage());
            }
        }

        return properties;
    }

    private static Properties getApplicationProperties() {
        if (null == applicationProperties) {
            applicationProperties = getProperties("application.properties");
        }
        return applicationProperties;
    }

    public static Properties getProfileProperties() {
        if (null == profileProperties) {
            profileProperties = new Properties();
            profileProperties.putAll(getApplicationProperties());
            String activeProfile = getApplicationProperties().getProperty("spring.profiles.default");
            if (BaseStringUtils.hasTextAfterTrim(activeProfile)) {
                Properties subProfileProperties = getProperties("application-" + activeProfile + ".properties");
                profileProperties.putAll(subProfileProperties);
            }
        }
        return profileProperties;
    }

}
