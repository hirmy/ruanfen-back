package com.ruanfen.canal;

import com.ruanfen.model.User;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

@Component
@CanalTable("user")
public class UserHandler implements EntryHandler<User> {
    @Override
    public void insert(User user) {
        System.out.println("user添加");
    }

    @Override
    public void update(User before, User after) {
        System.out.println("user更新");
    }

    @Override
    public void delete(User user) {
        System.out.println("user删除");
    }
}
