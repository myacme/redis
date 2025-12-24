package com.boot.redis.controller;


import com.boot.redis.bean.Result;
import com.boot.redis.redisson.RedissonLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ljx
 * @version 1.0.0
 * @create 2025/7/4 下午5:28
 */

@RestController
@RequestMapping("/redisson")
public class RedissonController {

    @Autowired
    private RedissonLock redissonLock;

    @PostMapping("/reduce")
    public Result reduceInventory() {
        try {
            redissonLock.reduceInventory();
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }
}
