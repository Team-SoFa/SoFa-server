package com.sw19.sofa.domain.ai.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WebScrapingService {
    private static final int MAX_CONTENT_LENGTH = 10000; // 적절한 길이로 조정

    public String extractContent(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(5000)
                    .get();

            String description = doc.select("meta[name=description]").attr("content");
            String bodyText = doc.select("article, main, .content, .article, .post, p").text();

            String content = description.isEmpty() ? bodyText : description + "\n" + bodyText;
            // 최대 길이 제한
            if (content.length() > MAX_CONTENT_LENGTH) {
                content = content.substring(0, MAX_CONTENT_LENGTH);
            }

            return content;

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