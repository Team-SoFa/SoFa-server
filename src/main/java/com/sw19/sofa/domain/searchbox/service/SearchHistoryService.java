package com.sw19.sofa.domain.searchbox.service;

import com.sw19.sofa.infra.redis.constants.RedisPrefix;
import com.sw19.sofa.infra.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {
    private final RedisService redisService;
    private static final int MAX_HISTORY_SIZE = 100;

    @SuppressWarnings("unchecked")
    public List<String> addSearchKeywordHistory(Long memberId, String keyword) {
        String key = "search:keywords:" + memberId;
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
        String key = "search:tags:" + memberId;
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

    public List<String> getSearchKeywordHistory(Long memberId) {
        String key = "search:keywords:" + memberId;
        @SuppressWarnings("unchecked")
        List<String> history = (List<String>) redisService.get(RedisPrefix.SEARCH_KEYWORD_HISTORY, key);
        return history != null ? history : new ArrayList<>();
    }

    public List<String> getSearchTagHistory(Long memberId) {
        String key = "search:tags:" + memberId;
        @SuppressWarnings("unchecked")
        List<String> history = (List<String>) redisService.get(RedisPrefix.SEARCH_TAG_HISTORY, key);
        return history != null ? history : new ArrayList<>();
    }
}
