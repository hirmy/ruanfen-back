package com.ruanfen.Docs;

import com.ruanfen.enums.Role;
import com.ruanfen.model.User;
import lombok.Data;

@Data
public class UserDoc {
    private long userId; // user_id 对应的字段，类型为 keyword
    private String userName; // user_name 对应的字段，类型为 text
    private String email; // email 对应的字段，类型为 keyword
    private String role; // role 对应的字段，类型为 text

    public UserDoc(User user){
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.role = user.getRole().getName();
    }
}
