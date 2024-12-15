package com.ruanfen.model;

import com.baomidou.mybatisplus.annotation.*;
import com.ruanfen.enums.FieldOfResearch;
import lombok.Data;

@Data
@TableName("researcher")
public class Researcher {
    @TableId(value = "researcher_id", type = IdType.AUTO) // 主键，自增
    private Integer researcherId;

    private String name;

    @EnumValue
    private FieldOfResearch fieldOfResearch; // 对应 ENUM 字段，使用 String 类型映射

    private String articleIds;

    private String patentIds;

    private String projectIds;

    private String awardIds;

    private String institution;

    private String awards;

    private Boolean claimed; // 对应 tinyint(1)，映射为 Boolean 类型
}
