package org.example.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Product {
    @CsvBindByName(column = "Url")
    private String url;
    @CsvBindByName(column = "Article")
    private String article;
}
