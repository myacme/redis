package com.boot.redis.bloomfilter;


import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 布隆过滤器配置类
 *
 * @author ljx
 * @version 1.0.0
 * @create 2025/12/24 15:57
 */
@Component
public class BloomFilterConfig {

    @Autowired
    private RedissonClient redissonClient;

    public RBloomFilter<String> getBloomFilter(String itemKey) {
        // 获取布隆过滤器实例
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter(itemKey);

        // 初始化布隆过滤器，预计元素数量和误判率
        bloomFilter.tryInit(100, 0.03);

        // 添加元素
        bloomFilter.add("item1");

        // 检查元素是否存在
        boolean exists = bloomFilter.contains("item1");
        System.out.println("元素item1是否存在：" + exists);
        return bloomFilter;
    }

    /**
     * 添加元素
     */
    public void add(String filterName, String value) {
        RBloomFilter<String> filter = getBloomFilter(filterName);
        if (filter != null) {
            filter.add(value);
        }
    }

    /**
     * 判断元素是否存在
     */
    public boolean mightContain(String filterName, String value) {
        RBloomFilter<String> filter = getBloomFilter(filterName);
        return filter != null && filter.contains(value);
    }

    /**
     * 清除布隆过滤器
     */
    public void clear(String filterName) {
        getBloomFilter(filterName).delete();
    }
}
