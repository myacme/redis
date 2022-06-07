package com.boot.redis.controller;


import com.boot.redis.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @Author zhushaojie
 * @Date 2020/6/8 10:17
 * @Version 1.0
 */
@RestController
public class StudentController {
	//===============redisTemplate 操作不同的数据类型，api和我们的命令是一样的===========
	//opsForValue 操作字符串 类似于String
	//redisTemplate.opsForValue();
	//opsForList 操作List 类似于List
	//redisTemplate.opsForList();
	//opsForSet 操作Set 类似于Set
	//redisTemplate.opsForSet();
	//opsForHash 操作hash 类似于hash
	//redisTemplate.opsForHash();
	//opsForHyperLogLog 操作HyperLogLog 类似于HyperLogLog
	//redisTemplate.opsForHyperLogLog();
	//opsForZSet 操作ZSet 类似于ZSet
	//redisTemplate.opsForZSet();
	//opsForGeo 操作Geo 类似于Geo
	//redisTemplate.opsForGeo();
	//除了基本的操作，我们常用的方法都可以直接通过redisTemplate操作，比如事务，和基本的CRUD
	//获取redis 的连接对象
//        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.flushDb();
//        connection.flushAll();


	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 存 相当于添加和修改
	 */
	@PostMapping("/set")
	public void set(@RequestBody Student student) {
		// 调用RedisTemplate封装好的k,v来存入实体类
		redisTemplate.opsForValue().set("student", student);
	}

	/**
	 * 取 相当于查询
	 */
	@GetMapping("/get/{key}")
	public Student get(@PathVariable("key") String key) {
		// 调用RedisTemplate获取方法
		return (Student) redisTemplate.opsForValue().get(key);
	}

	/**
	 * 删 相当于查询
	 * 删除成功返回false
	 * 删除失败返回true
	 */
	@DeleteMapping("/delete/{key}")
	public boolean delete(@PathVariable("key") String key) {
		// 调用RedisTemplate删除方法
		redisTemplate.delete(key);
		return redisTemplate.hasKey(key);
	}
}


