package org.example;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.ProjectException;
import org.example.model.Product;
import org.example.parser.ParserVertical;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;


@Slf4j
public class App {
    public static final String PROJECT_PATH = Paths.get("").toAbsolutePath().toString();
    public static final String FILE_CSV = PROJECT_PATH + File.separator + "result.csv";
    public static final String FILE_PROPERTIES = PROJECT_PATH + File.separator + "properties";
    private static final String DEFAULT_CSV_SEPARATOR = ",";

    private static final String DEFAULT_OFFSET = "0";
    private static final String DEFAULT_LIMIT = "10";

    public static void main(String[] args) {
        Properties properties = UtilsProperties.load(FILE_PROPERTIES);

        int limit = Integer.parseInt(properties.getProperty("limit", DEFAULT_LIMIT));
        int offset = Integer.parseInt(properties.getProperty("offset", DEFAULT_OFFSET));
        char separator = properties.getProperty("separator", DEFAULT_CSV_SEPARATOR).charAt(0);

        List<Product> products = null;
        try {
            products = ParserVertical.parseSite(offset, limit);
        } catch (ProjectException e) {
            log.error("Cant parse site {} {}", offset, limit);
            return;
        }

        try {
            saveCsv(products, FILE_CSV, separator);
        } catch (ProjectException e) {
            log.error("Cant save scv {} {}", products, FILE_CSV, e);
            return;
        }

        try {
            properties.setProperty("offset", offset + properties.size() + "");
            properties.setProperty("limit", limit + "");
            properties.setProperty("separator", separator + "");
            UtilsProperties.save(properties, FILE_PROPERTIES);
        } catch (ProjectException e) {
            log.error("Cant save properties {} {}", properties, FILE_PROPERTIES, e);
        }
    }

    private static void saveCsv(List<Product> list, String path, char separator) throws ProjectException {
        try (Writer writer = new FileWriter(path, true)) {
            StatefulBeanToCsv<Product> sbc = new StatefulBeanToCsvBuilder<Product>(writer)
                    .withSeparator(separator)
                    .build();
            sbc.write(list);
        } catch (Exception e) {
            log.error("Cant save csv", e);
        }
    }
}
