package com.sw19.sofa.domain.ai.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WebScrapingService {

    public String extractContent(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(5000)
                    .get();

            String description = doc.select("meta[name=description]").attr("content");
            String bodyText = doc.select("article, main, .content, .article, .post, p").text();

            return description.isEmpty() ? bodyText : description + "\n" + bodyText;

        } catch (Exception e) {
            log.error("URL 콘텐츠 추출 실패: {}", url, e);
            throw new RuntimeException("URL에서 콘텐츠를 추출할 수 없습니다: " + url);
        }
    }

    public String extractTitle(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(5000)
                    .get();

            return doc.title();
        } catch (Exception e) {
            log.error("URL 제목 추출 실패: {}", url, e);
            throw new RuntimeException("URL에서 제목을 추출할 수 없습니다: " + url);
        }
    }
}