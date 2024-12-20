package com.sw19.sofa.domain.searchbox.service;

import com.sw19.sofa.infra.redis.constants.RedisPrefix;
import com.sw19.sofa.infra.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sw19.sofa.global.common.constants.Constants.SEARCH_KEYWORDS_PREFIX;
import static com.sw19.sofa.global.common.constants.Constants.SEARCH_TAGS_PREFIX;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {
    private final RedisService redisService;
    private static final int MAX_HISTORY_SIZE = 100;

    @SuppressWarnings("unchecked")
    public List<String> addSearchKeywordHistory(Long memberId, String keyword) {
        String key = SEARCH_KEYWORDS_PREFIX + memberId;
        List<String> history = (List<String>) redisService.get(RedisPrefix.SEARCH_KEYWORD_HISTORY, key);
        if (history == null) {
            history = new ArrayList<>();
        }

        history.remove(keyword);
        history.add(0, keyword);

        if (history.size() > MAX_HISTORY_SIZE) {
            history = history.subList(0, MAX_HISTORY_SIZE);
        }

        redisService.save(RedisPrefix.SEARCH_KEYWORD_HISTORY, key, history);
        return history;
    }

    @SuppressWarnings("unchecked")
    public List<String> addSearchTagHistory(Long memberId, String tagName) {
        String key = SEARCH_TAGS_PREFIX + memberId;
        List<String> history = (List<String>) redisService.get(RedisPrefix.SEARCH_TAG_HISTORY, key);
        if (history == null) {
            history = new ArrayList<>();
        }

        history.remove(tagName);
        history.add(0, tagName);

        if (history.size() > MAX_HISTORY_SIZE) {
            history = history.subList(0, MAX_HISTORY_SIZE);
        }

        redisService.save(RedisPrefix.SEARCH_TAG_HISTORY, key, history);
        return history;
    }

    @SuppressWarnings("unchecked")
    public List<String> addSearchTagHistory(Long memberId, List<String> tagNames) {
        // 여러 태그 동시 추가 지원
        return tagNames.stream()
                .map(tagName -> addSearchTagHistory(memberId, tagName))
                .flatMap(List::stream)
                .distinct()
                .toList();
    }

    public List<String> getSearchKeywordHistory(Long memberId) {
        String key = SEARCH_KEYWORDS_PREFIX + memberId;
        @SuppressWarnings("unchecked")
        List<String> history = (List<String>) redisService.get(RedisPrefix.SEARCH_KEYWORD_HISTORY, key);
        return history != null ? history : new ArrayList<>();
    }

    public List<String> getSearchTagHistory(Long memberId) {
        String key = SEARCH_TAGS_PREFIX + memberId;
        @SuppressWarnings("unchecked")
        List<String> history = (List<String>) redisService.get(RedisPrefix.SEARCH_TAG_HISTORY, key);
        return history != null ? history : new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public void deleteSearchKeywordHistory(Long memberId, String keyword) {
        String key = SEARCH_KEYWORDS_PREFIX + memberId;
        List<String> history = (List<String>) redisService.get(RedisPrefix.SEARCH_KEYWORD_HISTORY, key);

        if (history != null) {
            history.remove(keyword);
            redisService.save(RedisPrefix.SEARCH_KEYWORD_HISTORY, key, history);
        }
    }

    @SuppressWarnings("unchecked")
    public void deleteSearchTagHistory(Long memberId, String tagId) {
        String key = SEARCH_TAGS_PREFIX + memberId;
        List<String> history = (List<String>) redisService.get(RedisPrefix.SEARCH_TAG_HISTORY, key);

        if (history != null) {
            history.remove(tagId);
            redisService.save(RedisPrefix.SEARCH_TAG_HISTORY, key, history);
        }
    }
}
