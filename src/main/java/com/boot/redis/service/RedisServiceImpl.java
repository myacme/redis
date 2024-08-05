package com.boot.redis.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ljx
 * @version 1.0.0
 * @create 2024/8/5 上午10:57
 */
@Service
public class RedisServiceImpl {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String USER_SIGN_KEY = "LOGIN_KEY";

    public int sign() {
        // 1.获取当前登录用户
        Long userId = 100000L;
        // 2.获取日期
        LocalDateTime now = LocalDateTime.now();
        // 3.拼接key
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = USER_SIGN_KEY + userId + keySuffix;
        // 4.获取今天是本月的第几天
        int dayOfMonth = now.getDayOfMonth();
        // 5.写入Redis SETBIT key offset 1
        stringRedisTemplate.opsForValue().setBit(key, dayOfMonth - 1, true);
        return 1;
    }
}