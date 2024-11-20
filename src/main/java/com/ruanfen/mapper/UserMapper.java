package com.ruanfen.mapper;

import com.ruanfen.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<User> allUser();

    @Insert("insert into user(username, password, create_time, update_time) " +
            "values(#{username}, #{password}, now(), now())")
    void add(String username, String password);

    @Select("select * from user where username = #{username}")
    User findByUserName(String username);
}
