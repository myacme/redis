package jedis;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis测试
 *
 * @author MyAcme
 * @since 2021年5月15日
 */
public class JedisDemo1 {
	public static void main(String[] args) {
		demo2();
	}


	public static void demo1() {
		// 1. 设置IP地址和端口
		Jedis jedis = new Jedis("192.168.1.33", 6379);
		jedis.auth("1qaz@WSXredis");
		//string
		jedis.set("string", "string");
		System.out.println(jedis.get("string"));
		//list
		jedis.rpush("list", "list1", "list2");
		System.out.println(jedis.lindex("list", 0));
		//set
		jedis.sadd("set", "set1", "set2", "set3");
		System.out.println(jedis.spop("set"));
		//zset
		jedis.zadd("zset", 1, "zset1");
		jedis.zadd("zset", 2, "zset2");
		jedis.zadd("zset", 3, "zset3");
		System.out.println(jedis.zrange("zset", 0, 1));
		//hash
		jedis.hset("hash", "hash1", "hash1");
		System.out.println(jedis.hget("hash", "hash1"));
		jedis.hgetAll("hash");
		// 4.释放资源
		jedis.close();
	}

	public static void demo2() {
		// 获取连接池配置对象
		JedisPoolConfig config = new JedisPoolConfig();
		// 设置最大连接数
		config.setMaxTotal(30);
		// 设置最大的空闲连接数
		config.setMaxIdle(10);
		// 获得连接池: JedisPool jedisPool = new JedisPool(poolConfig,host,port);
		JedisPool jedisPool = new JedisPool(config, "192.168.1.33", 6379);
		// 获得核心对象：jedis
		Jedis jedis = null;
		try {
			// 通过连接池来获得连接
			jedis = jedisPool.getResource();
			jedis.auth("1qaz@WSXredis");
			// 设置数据
			jedis.set("name", "张三");
			// 获取数据
			String value = jedis.get("name");
			System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			if (jedis != null) {
				jedis.close();
			}
			// 释放连接池
			if (jedisPool != null) {
				jedisPool.close();
			}
		}
	}
}