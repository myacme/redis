package jedis;

import redis.clients.jedis.Jedis;

import java.util.List;

public class BloomFilterExample {
    public static void main(String[] args) {
        // 连接到 Redis 服务器
        Jedis jedis = new Jedis("192.168.75.128", 6379);
        jedis.auth("redis@123");
        // 定义布隆过滤器的名称和要添加的元素
        String bloomFilterKey = "myBloomFilter";
        String element = "exampleElement";
        // 将元素添加到布隆过滤器中
        List<String> isAdded = jedis.blpop(bloomFilterKey, element);
        System.out.println("元素成功添加到布隆过滤器中");
        // 关闭连接
        jedis.close();
    }
}
