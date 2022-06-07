package com.boot.redis.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author
 * @Date 2020/6/8 10:08
 * @Version 1.0
 * 一定要实现序列化，如果没有实现序列化无法存入redis
 */
@Data
public class Student implements Serializable {
	private Integer id;
	private String name;
	private Double score;
	private Date birthday;
}

