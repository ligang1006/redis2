package net.gaven.redisdemo.service.impl;

import net.gaven.redisdemo.domain.User;
import net.gaven.redisdemo.mapper.UserMapper;
import net.gaven.redisdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @program: redis-demo
 * @description:
 * @author: Mr.lee
 * @create: 2020-04-01 14:36
 **/
@Service
// 本类内方法指定使用缓存时，默认的名称就是userInfoCache
@CacheConfig(cacheNames = "userInfoCache")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class )
public class UserSpringCacheService implements UserService {

    @Autowired
    UserMapper userMapper;



    public User findNoCache(String id){
        return userMapper.find(id);
    }

    /**
     *  @Cacheable 会先查询缓存，如果缓存中存在，则不执行方法
     * @param id
     * @return
     */
    @Override
    @Nullable
    @Cacheable(key = "#p0")
    public User find(String id) {
        System.err.println("根据id=" + id +"获取用户对象，从数据库中获取");
        Assert.notNull(id,"id不用为空");
        return userMapper.find(id);
    }

    @Override
    @Cacheable
    public List<User> findAll() {

        return userMapper.findAll();
    }

    @Override
    @CachePut(key = "#p0.id")
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
//    @CachePut(key = "#p0.id")
    @CachePut(value = "key", keyGenerator = "simpleKeyGenerator")
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    @CacheEvict(allEntries = true)
    public int deleteAll() {
        return userMapper.deleteAll();
    }

    @Override
    @CacheEvict(key = "#p0")
    public int delete(String id) {
        return userMapper.delete(id);
    }

    @Override
    @Cacheable(key = "#p0")
    public List<User> query(String userName) {
        return userMapper.query(userName);
    }



    @Override
    @Cacheable(key = "#p0")
    public User findById(String id) {
        return userMapper.find(id);
    }


    /**
     * @Cacheable 会先查询缓存，如果缓存中存在，则不执行方法
     *  value 对应之前的key
     *   @Bean
     *   public KeyGenerator simpleKeyGenerator(){
     *
     *
     *  keyGenerator = "simpleKeyGenerator"  必须要与自己设置的Bean完全一致
     * UserInfoList
     * 设置过期时间
     * @param id
     * @return
     */

    @Override
    @Nullable
    @Cacheable(value = "UserInfoList", keyGenerator = "simpleKeyGenerator")
    public User findByIdTtl(String id){
        System.err.println("根据id=" + id +"获取用户对象，从数据库中获取");
        Assert.notNull(id,"id不用为空");
        return userMapper.find(id);
    }
}
