package com.sw19.sofa.infra.redis.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;

@Getter
@AllArgsConstructor
public enum RedisPrefix {
    REFRESH_TOKEN(Duration.ofDays(180)),
    SING_UP(Duration.ofMinutes(10)),
    PROFILE(Duration.ofDays(1))
    ;

    final Duration duration;
}