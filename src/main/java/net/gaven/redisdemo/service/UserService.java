package net.gaven.redisdemo.service;

import net.gaven.redisdemo.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserService {


    User find(String id);

    List<User> findAll();


    int update(User user);


    int insert(User user);




    int deleteAll();


    int delete( String id);

    List<User> query(String userName);

    User findById(String id);

    User findByIdTtl(String id);
}
