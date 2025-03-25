package com.mjutarzan.tarzan.global.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 데이터 저장
    public void saveData(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void saveData(String key, String value, Integer expiration) {
        redisTemplate.opsForValue().set(key, value, expiration, TimeUnit.SECONDS);
    }

    // 데이터 조회
    public String getData(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    // 데이터 삭제
    public Boolean deleteData(String key) {
        return redisTemplate.delete(key);
    }


}
