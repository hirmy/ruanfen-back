package com.ruanfen.model;

import com.baomidou.mybatisplus.annotation.*;
import com.ruanfen.enums.FieldOfResearch;
import com.ruanfen.enums.PatentType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("patent")
public class Patent {
    @TableId(value = "patent_id", type = IdType.AUTO) // 主键自动增长
    private Integer patentId;

    private String patentName;
    @EnumValue
    private PatentType patentType; // 枚举类型字段，用 String 表示
    private String applicationNum;
    private String publicationNum;
    private String authorizationNum;
    private LocalDateTime applicationDate;
    private LocalDateTime publicationDate;
    private LocalDateTime authorizationDate;
    private String mainClaim;
    private String abstractContent; //摘要 避免冲突，重命名为 `abstractContent`
    private String applicants;
    private String inventorsId;
    @EnumValue
    private FieldOfResearch fieldOfResearch;
    private Integer views;
}
