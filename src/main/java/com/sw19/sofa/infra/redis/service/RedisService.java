package com.sw19.sofa.infra.redis.service;

import com.sw19.sofa.infra.redis.constants.RedisPrefix;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void save(String key, Object data) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, data);
    }

    public void save(String key, Object data, Duration duration) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    public void save(RedisPrefix prefix, String key, Object data) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(prefix.name() + key, data);
    }

    public void save(RedisPrefix prefix, String key, Object data, Duration duration) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(prefix.name() + key, data, duration);
    }

    public Object get(String key) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        return  values.get(key);
    }

    public Object get(RedisPrefix prefix, String key) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        return values.get(prefix.name() + key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void delete(RedisPrefix prefix, String key) {
        redisTemplate.delete(prefix.name() + key);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Boolean hasKey(RedisPrefix prefix, String key) {
        return redisTemplate.hasKey(prefix.name() + key);
    }

    public Boolean hasNoKey(String key) {
        return !redisTemplate.hasKey(key);
    }

    public Boolean hasNoKey(RedisPrefix prefix, String key) {
        return !redisTemplate.hasKey(prefix.name() + key);
    }
}
