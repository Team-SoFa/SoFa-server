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

            // 메타 태그의 description이 있다면 추가
            String description = doc.select("meta[name=description]").attr("content");

            // 본문 내용 추출 (주요 텍스트 콘텐츠)
            String bodyText = doc.select("p").text();

            // description과 본문 내용 조합
            return description + "\n" + bodyText;

        } catch (Exception e) {
            log.error("URL에서 콘텐츠 추출 중 오류 발생: {}", url, e);
            throw new RuntimeException("URL에서 콘텐츠를 추출할 수 없습니다.", e);
        }
    }
}