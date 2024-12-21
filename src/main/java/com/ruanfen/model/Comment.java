package com.ruanfen.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

public class Comment {
    @TableId(value = "comment_id", type = IdType.AUTO) // 主键自增
    private Integer commentId;
    private String commentContent;
    private LocalDateTime commentTime;
    private Integer userId;
    private Integer achievementType;
    private Integer achievementId;
    
}
