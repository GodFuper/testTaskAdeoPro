package org.example.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Product {
    @CsvBindByPosition(position = 1)
    private String url;
    @CsvBindByPosition(position = 0)
    private String article;
}
