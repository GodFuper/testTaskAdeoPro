package org.example.parser;

import org.example.model.Product;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserVerticalTest {

    @Test
    void parseInputStream() {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> urls = Arrays.asList("https://vertical.ru/product/abazhur-dlya-svetilnika-nastennyy-bannye-shtuchki-lipa-25-kh-16-kh-30-sm/",
                "https://vertical.ru/product/abazhur-dlya-svetilnika-nastennyy-bannye-shtuchki-lipa-30-kh-10-kh-36-sm/",
                "https://vertical.ru/product/abazhur-dlya-svetilnika-uglovoy-bannye-shtuchki-kosichka-lipa-27-kh-11-kh-31-sm/",
                "https://vertical.ru/product/abazhur-dlya-svetilnika-uglovoy-bannye-shtuchki-lipa-31-kh-10-kh-27-sm/",
                "https://vertical.ru/product/abazhur-dlya-svetilnika-uglovoy-bannye-shtuchki-ploskiy-obozhzhennaya-lipa-29-5-kh-27-5-kh-6-sm/");

        for (String url : urls) {
            stringBuilder.append("<url><loc>").append(url).append("</loc></url>").append("\n");
        }

        int offset = 2;
        int limit = 1;

        InputStream inputStream = new ByteArrayInputStream(stringBuilder.toString().getBytes());
        List<Product> products = ParserVertical.parseInputStream(offset, limit, inputStream);

        assertEquals(1, products.size());
        assertEquals("228862", products.get(0).getArticle());
    }

    @Test
    void parseArticleFromUrl() {
        String url = "https://vertical.ru/product/lopata-dlya-uborki-snega-fiskars-solid-s-alyuminievym-cherenkom-355-x-450-mm/";
        assertEquals("227020", ParserVertical.parseArticleFromUrl(url));
    }
}