package com.ruanfen.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("award")
public class Award {
    @TableId(value = "award_id", type = IdType.AUTO) // 主键自增
    private Integer awardId;

    private String awardName; // 奖项名称

    private String awardType; // 奖项类型

    private LocalDateTime awardDate; // 获奖时间

    private Integer winnerId; // 外键，获奖者ID

    private String awardDescription; // 奖项介绍
}
