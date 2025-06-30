package jedis;


import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ljx
 * @version 1.0.0
 * @create 2025/6/30 上午11:13
 */
public class BloomFilterRedisson {

    public static void main(String[] args) {
        patchingConsum();
    }

    public static void patchingConsum(){
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://192.168.75.128:6379");
        singleServerConfig.setPassword("redis@123");
        RedissonClient redissonClient = Redisson.create(config);
        RBloomFilter<String> bloom = redissonClient.getBloomFilter("name");
        // 初始化布隆过滤器；  大小:100000，误判率:0.01
        bloom.tryInit(100L, 0.01);
        // 新增10万条数据
        for(int i=0;i<100;i++) {
            bloom.add("name" + i);
        }
        // 判断不存在于布隆过滤器中的元素
        List<String> notExistList = new ArrayList<>();
        for(int i=0;i<100;i++) {
            String str = "name" + i;
            boolean notExist = bloom.contains(str);
            if (notExist) {
                notExistList.add(str);
            }
        }
        if (!CollectionUtils.isEmpty(notExistList) && notExistList.size() > 0 ) {
            System.out.println("误判次数:"+notExistList.size());
        }

    }



}
