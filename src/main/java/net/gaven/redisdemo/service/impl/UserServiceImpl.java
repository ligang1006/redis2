package net.gaven.redisdemo.service.impl;

import net.gaven.redisdemo.domain.User;
import net.gaven.redisdemo.mapper.UserMapper;
import net.gaven.redisdemo.service.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: redis-demo
 * @description:
 * @author: Mr.lee
 * @create: 2020-03-31 21:14
 **/
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User find(String id) {
        return userMapper.find(id);
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public int update(User user) {

        return userMapper.update(user);
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int deleteAll() {
        return userMapper.deleteAll();
    }

    @Override
    public int delete(String id) {
        return userMapper.delete(id);
    }

    @Override
    public List<User> query(String userName) {
        return userMapper.query(userName);
    }

    @Override
    public User findById(String id) {

        return null;
    }

    @Override
    public User findByIdTtl(String id) {
        return null;
    }


}
