package com.ruanfen.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Comment {
    @TableId(value = "comment_id", type = IdType.AUTO) // 主键自增
    private Integer commentId;
    private String commentContent;
    private Date commentTime;
    private Integer userId;
    private Integer achievementType;
    private Integer achievementId;

}