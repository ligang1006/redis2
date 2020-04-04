package net.gaven.redisdemo.mapper;

import net.gaven.redisdemo.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {

    @Select("select * from sys_user where id =#{id}")
    User find(@Param("id")String id);

    @Update("update sys_user set user_name=#{userName} where id=#{id} ")
    int update(User user);

    @Insert("insert into sys_user (id, user_name) values (#{id},#{userName})")
    int insert(User user);

    @Delete("delete from sys_user")
    int deleteAll();

    @Delete("delete from sys_user where id=#{id}")
    int delete(@Param("id") String id);


    @Select("select * from sys_user")
    List<User> findAll();

    List<User> query(@Param("user_name") String userName);

}
