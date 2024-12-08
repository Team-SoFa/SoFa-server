package com.sw19.sofa.global.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ImageExtractor {

    public String extractMainImage(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(5000)
                    .get();

            Elements ogImage = doc.select("meta[property=og:image]");
            if (!ogImage.isEmpty()) {
                return ogImage.attr("content");
            }

            Elements twitterImage = doc.select("meta[name=twitter:image]");
            if (!twitterImage.isEmpty()) {
                return twitterImage.attr("content");
            }

            Elements images = doc.select("img");
            if (!images.isEmpty()) {
                for (Element img : images) {
                    String src = img.absUrl("src");
                    if (!src.isEmpty() && isValidImageUrl(src)) {
                        return src;
                    }
                }
            }

            return null;
        } catch (IOException e) {
            return null;
        }
    }

    private boolean isValidImageUrl(String url) {
        String lowercaseUrl = url.toLowerCase();
        return lowercaseUrl.endsWith(".jpg") ||
                lowercaseUrl.endsWith(".jpeg") ||
                lowercaseUrl.endsWith(".png") ||
                lowercaseUrl.endsWith(".gif") ||
                lowercaseUrl.endsWith(".webp");
    }
}