package net.gaven.redisdemo.controller;

import net.gaven.redisdemo.schedule.ClusterRedisLock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: redis-demo
 * @description:
 * @author: Mr.lee
 * @create: 2020-04-04 10:34
 **/
@RestController
public class ClusterRedisLockController {
    @Resource
    private ClusterRedisLock clusterRedisLock;
    @GetMapping("/test/lock")
    public String testRedis(){
        clusterRedisLock.getClusterLock();
        return "success";
    }

}
