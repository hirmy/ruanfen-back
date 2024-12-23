package com.ruanfen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruanfen.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserMapper extends BaseMapper<User> {

    void addUser(String name, String securePwd);
}
