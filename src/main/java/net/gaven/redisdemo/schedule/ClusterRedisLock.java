package net.gaven.redisdemo.schedule;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * @program: redis-demo
 * @description: redis实现分布式锁
 * @author: Mr.lee
 * @create: 2020-04-04 10:09
 **/
@Service
public class ClusterRedisLock {
    private static final Logger logger = LoggerFactory.getLogger(ClusterRedisLock.class);

    @Resource
    private RedisTemplate redisTemplate;

    private static final String PREFIX = "ClusterRedisLock";

    private String key="prefix"+PREFIX;
    boolean nxRet = false;

    private String valueHost = null;

    public void getClusterLock() {
        InetAddress addr = null;
        String ip = null;

        try {
            addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress().toString(); //获取本机ip
            valueHost = addr.getHostName().toString(); //获取本机计算机名称
            nxRet=redisTemplate.opsForValue().setIfAbsent(key,valueHost,5000, TimeUnit.SECONDS);
            System.out.println("ip" + ip + "hostName" + valueHost);
            /**
             * 获取失败
             */
            if (!nxRet){
                String name=(String) redisTemplate.opsForValue().get(key);
                System.out.println("lock is used by："+name);
                logger.info("lock is used by："+name);
            }else {
                redisTemplate.opsForValue().set(key,valueHost,20000);
            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        }finally {
            redisTemplate.delete(key);
        }



    }

}
