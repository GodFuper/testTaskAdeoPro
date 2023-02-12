package org.example;

import org.example.exception.ProjectException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class UtilsPropertiesTest {

    @Test
    void saveAndLoad() throws IOException, ProjectException {
        File file = File.createTempFile("test", null);
        Properties properties = new Properties();
        String key = "test";
        String value = "123456";

        properties.setProperty(key, value);
        UtilsProperties.save(properties, file.getAbsolutePath());

        Properties loadProperties = UtilsProperties.load(file.getAbsolutePath());

        assertEquals(value, loadProperties.getProperty(key));
    }

}