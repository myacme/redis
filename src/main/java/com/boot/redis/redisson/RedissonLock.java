package com.boot.redis.redisson;


import com.boot.redis.bloomfilter.BloomFilterConfig;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author ljx
 * @version 1.0.0
 * @create 2025/7/4 下午5:15
 */
@Slf4j
@Component
public class RedissonLock {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private StringRedisTemplate redisTemplate;
    
    @Autowired
    private BloomFilterConfig bloomFilter;

    public void reduceInventory() {
        String key = "inventory";
        RLock lock = redissonClient.getLock("lock");
        lock.lock();
        try {
            log.info("{}获取锁：{}成功", Thread.currentThread().getName(), "lock");
            int inventory = Integer.parseInt(Objects.requireNonNull(redisTemplate.opsForValue().get(key)));
            if (inventory > 0) {
                redisTemplate.opsForValue().set(key, String.valueOf(inventory - 1));
            }
        } catch (Exception e) {
            log.error("{}扣减库存失败", Thread.currentThread().getName());
        } finally {
            lock.unlock();
        }
    }



    public boolean reduceInventoryWithBloomFilter(String id) {

        RBloomFilter rbf = bloomFilter.getBloomFilter("inventory");
        // 检查布隆过滤器，快速判断商品是否存在
        if (!rbf.contains(id)) {
            log.warn("商品 {} 可能不存在", id);
            return false;
        }

        String inventoryKey = "inventory:" + id;
        RLock lock = redissonClient.getLock("inventoryLock:" +  id);

        try {
            lock.lock();
            String inventoryStr = redisTemplate.opsForValue().get(inventoryKey);
            if (inventoryStr != null) {
                int inventory = Integer.parseInt(inventoryStr);
                if (inventory > 0) {
                    redisTemplate.opsForValue().set(inventoryKey, String.valueOf(inventory - 1));
                    return true;
                }
            }
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return false;
    }

}
