package com.ruanfen;

import com.ruanfen.model.User;
import com.ruanfen.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {
    @Autowired
    UserService userService;

    @Test
    public void testSelect(){
        User user = userService.query()
                        .eq("id", 1)
                                .one();
        System.out.println(user);
    }
}
