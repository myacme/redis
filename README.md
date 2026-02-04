# Redis Demo 项目

## 项目概述

这是一个基于Spring Boot的Redis演示项目，展示了Redis在实际应用中的各种使用场景和技术实现。项目集成了Redisson客户端、布隆过滤器、分布式锁等高级特性。

## 技术栈

- **Spring Boot**: 2.5.0
- **Java版本**: 1.8
- **Redis客户端**: 
  - Redisson 3.13.1 (主要客户端)
  - Jedis 5.1.0 (备用客户端)
- **连接池**: Apache Commons Pool2
- **其他依赖**: Lombok, Spring Web

## 项目结构

```
src/main/java/com/boot/redis/
├── bean/                    # 统一响应结果封装
│   ├── Result.java         # API统一返回结果
│   └── ResultStatus.java   # 返回状态枚举
├── bloomfilter/            # 布隆过滤器相关
│   └── BloomFilterConfig.java  # 布隆过滤器配置类
├── config/                 # 配置类
│   └── RestTemplateConfig.java # REST模板配置
├── controller/             # 控制器层
│   ├── RedissonController.java # Redisson相关接口
│   └── StudentController.java  # 学生信息操作接口
├── entity/                 # 实体类
│   └── Student.java        # 学生实体
├── redisson/               # Redisson相关组件
│   ├── BloomFilterRedisson.java # Redisson布隆过滤器
│   ├── RedissonConfig.java      # Redisson配置
│   └── RedissonLock.java        # 分布式锁实现
├── service/                # 服务层
│   └── RedisServiceImpl.java    # Redis服务实现
└── RedisApplication.java   # 启动类

src/main/resources/
├── application.yml         # 应用配置文件
└── logback.xml            # 日志配置
```

## 核心功能

### 1. Redis基础操作
- 字符串操作 (String)
- 列表操作 (List)  
- 集合操作 (Set)
- 哈希操作 (Hash)
- 有序集合操作 (ZSet)
- 地理位置操作 (Geo)
- HyperLogLog操作

### 2. Redisson分布式特性
- **分布式锁**: 基于Redisson实现的商品库存扣减
- **布隆过滤器**: 高效的大数据去重判断
- **配置管理**: Redisson客户端自动配置

### 3. 实际应用场景示例
- **签到系统**: 使用Redis的Bitmap实现用户月度签到
- **缓存管理**: 基本的CRUD操作示例
- **并发控制**: 分布式锁防止超卖问题

## 配置说明

### Redis连接配置
```yaml
spring:
  redis:
    host: 127.0.0.1      # Redis服务器地址
    port: 6379           # Redis端口
    maxIdle: 50          # 最大空闲连接数
    maxWaitMillis: 5000  # 最大等待时间
    maxTotal: 500        # 最大连接数
```

### 端口配置
```yaml
server:
  port: 8080            # 应用启动端口
```

## 主要API接口

### 学生信息操作
- `GET /set/{key}` - 设置学生信息
- `GET /get/{key}` - 获取学生信息  
- `DELETE /delete/{key}` - 删除学生信息

### Redisson相关
- `POST /redisson/reduce` - 执行库存扣减（带分布式锁）

## 运行方式

1. 确保本地已安装并启动Redis服务
2. 克隆项目到本地
3. 使用Maven构建项目：
   ```bash
   mvn clean install
   ```
4. 启动应用：
   ```bash
   mvn spring-boot:run
   ```
5. 访问地址：http://localhost:8080

## 注意事项

- 项目默认连接本地Redis (127.0.0.1:6379)
- 如需连接远程Redis，请修改`application.yml`中的配置
- 布隆过滤器的初始化参数可根据实际需求调整
- 分布式锁的超时时间建议根据业务场景合理设置

## 开发环境

- **IDE**: IntelliJ IDEA 2025.2
- **构建工具**: Maven
- **JDK**: 1.8
- **操作系统**: Windows 25H2

## 版本信息

- **项目版本**: 0.0.1-SNAPSHOT
- **最后更新**: 2026年2月

---
*该项目主要用于学习和演示Redis在Spring Boot应用中的各种使用方式*