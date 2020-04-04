package net.gaven.redisdemo.service;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: redis-demo
 * @description:
 * @author: Mr.lee
 * @create: 2020-03-31 11:41
 **/
@Service
public class RedisServiceTest {
    @Resource
    RedisTemplate redisTemplate;
    public Object testRt(String name){
//        //连接本地的 Redis 服务
//        Jedis jedis = new Jedis("localhost");
//        System.out.println("Connection to server sucessfully");
//        //设置 redis 字符串数据
//        jedis.set("w3ckey", "Redis tutorial");
//        // 获取存储的数据并输出
//        System.out.println("Stored string in redis:: "+ jedis.get("w3ckey"));

        redisTemplate.opsForValue().set("school",name);
        String name1 =(String)redisTemplate.opsForValue().get("school");
        System.out.println("name:"+name1);
        return name1;

    }

}
