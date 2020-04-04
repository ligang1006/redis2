package net.gaven.redisdemo.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.gaven.redisdemo.domain.User;
import net.gaven.redisdemo.mapper.UserMapper;
import net.gaven.redisdemo.service.UserService;
import net.gaven.redisdemo.service.impl.UserSpringCacheService;
import net.gaven.redisdemo.util.RedisService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: redis-demo
 * @description:
 * @author: Mr.lee
 * @create: 2020-03-31 21:20
 **/
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private static final String key = "userCache_";
//    @Resource
//    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserSpringCacheService springCacheService;

    @Resource
    private RedisService redisService;
//
//    public List<User> findAll(){
//        return userService.query();
//    }

  /*  @GetMapping("/find_by_id")
    public User find(@RequestParam("id") String id){
        return userService.find(id);
    }
    @GetMapping("/get_user_cache")
    public User cacheFind(@RequestParam("id") String id){
        long start= System.nanoTime();
//
//        System.out.println("====start"+start);
        User user =  (User) redisService.get(key + id);
//        long redisend = System.nanoTime();
//        System.out.println("====redisend"+redisend);
//        System.out.println((redisend-start)/1000000000);
        if (null==user){
            long mystart=System.nanoTime();
//            System.out.println("====mystart"+mystart);

            User userDB = userService.find(id);
//            long mysqlend=System.nanoTime();
//            System.out.println("====mysqlend"+mysqlend);
//            System.out.println((mysqlend-mystart)/1000000000);

            redisService.set(key+id, userDB);

            return userDB;
        }else {
            return user;
        }

    }
*/


    @GetMapping("/getNoCache")
    public User getNoCache(String id) {
      return springCacheService.findNoCache(id);
    }


    @RequestMapping("/getByCache")
    @ResponseBody
    public User getByCache(String id) {
        User user = springCacheService.findById(id);
        return user;
    }


    @PostMapping("/update")
    public int update(@RequestBody User user) {
        int update = springCacheService.update(user);
        return update;
    }

    @PostMapping("/insert")
    public int insert(@RequestBody User user) {
        return springCacheService.insert(user);


    }

    @GetMapping("/find_all")
    public Object findAll(String userName) {
        List<User> query = springCacheService.findAll();
        return query;

    }

    @GetMapping("/get_expire")
    public User findByIdTtl(String id) {

        User user = springCacheService.findByIdTtl(id);
        return user;
    }


}
