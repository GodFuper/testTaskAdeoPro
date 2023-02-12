package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.ProjectException;

import java.io.*;
import java.util.Properties;
@Slf4j
public class UtilsProperties {

    public static void save(Properties properties, String path) throws ProjectException {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            properties.store(fos, null);
        } catch (IOException e) {
            throw new ProjectException("Cant save properties");
        }
    }

    public static Properties load(String path) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(path)) {
            properties.load(fis);
        } catch (IOException e) {
            log.debug("Create properties");
        }
        return properties;
    }

}
