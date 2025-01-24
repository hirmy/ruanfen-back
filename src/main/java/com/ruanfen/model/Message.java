package com.ruanfen.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("message")
public class Message {
    @TableId(value = "message_id", type = IdType.AUTO) // 主键，自增
    private Integer messageId;

    @TableField("sender_id") // 发送者ID
    private Integer senderId;

    @TableField("receiver_id") // 接收者ID
    private Integer receiverId;

    @TableField("category") // 消息类别
    private String category;

    @TableField("title") // 消息标题
    private String title;

    @TableField("content") // 消息内容
    private String content;

    @TableField("created_time") // 创建时间
    private LocalDateTime createdTime;

    @TableField("updated_time") // 更新时间
    private LocalDateTime updatedTime;

    @TableField("if_confirmed") // 是否确认接收
    private Boolean ifConfirmed;
}