package org.example.parser;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.ProjectException;
import org.example.model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class ParserVertical {

    private static final String URL_SITE = "https://vertical.ru/sitemap.xml";

    private static final String REGEX_START = "<loc>";
    private static final String REGEX_END = "</loc>";

    public static List<Product> parseSite(int offset, int limit) throws ProjectException {
        try {
            URL url = new URL(URL_SITE);
            url.openConnection();
            return parseInputStream(offset, limit, url.openStream());
        } catch (IOException e) {
            throw new ProjectException("Cant open url: " + URL_SITE);
        }
    }

    private static List<Product> parseInputStream(int offset, int limit, InputStream inputStream) {
        List<Product> products = new ArrayList<>();
        int i = 0;
        try (Scanner scanner = new Scanner(inputStream)) {
            scanner.useDelimiter("");
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNext() && limit > 0) {
                String word = scanner.next();
                stringBuilder.append(word);

                if (stringBuilder.toString().contains(REGEX_END)) {
                    String str = stringBuilder.toString();
                    String urlStr = str.substring(str.indexOf(REGEX_START) + REGEX_START.length(), str.length() - REGEX_END.length());
                    if (isProductUrl(urlStr) && offset <= i) {
                        String article = parseArticleFromUrl(urlStr);
                        if (article != null) {
                            log.debug("{} {}", urlStr, article);
                            products.add(new Product(urlStr, article));
                            limit--;
                        } else {
                            log.warn("Can get article from {}", urlStr);
                        }
                    }
                    stringBuilder = new StringBuilder();
                }
                i++;
            }
            return products;
        }
    }

    public static String parseArticleFromUrl(String urlProduct) {
        Document doc = null;
        try {
            doc = Jsoup.connect(urlProduct).get();
        } catch (IOException e) {
            return null;
        }

        Elements elements = doc.getElementsByClass("art");
        if (elements.isEmpty()) {
            return null;
        }
        Element fullArticle = elements.get(0);
        if (fullArticle.children().isEmpty()) {
            return null;
        }
        Element numberArticle = fullArticle.child(0);
        return numberArticle.text();
    }

    private static boolean isProductUrl(String urlStr) {
        return urlStr.contains("product");
    }

}
