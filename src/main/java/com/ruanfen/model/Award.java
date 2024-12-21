package com.ruanfen.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("award")
public class Award {
    @TableId(value = "award_id", type = IdType.AUTO) // 主键，自增
    private Integer awardId;

    @TableField("award_name") // 显式指定字段名
    private String awardName;

    @TableField("award_type")
    private String awardType;

    @TableField("award_date")
    private LocalDate awardDate;

    @TableField("winner_id") // 外键关联的列
    private Integer winnerId; // 外键，指向 Researcher 的 researcherId

    @TableField("award_description")
    private String awardDescription;
}