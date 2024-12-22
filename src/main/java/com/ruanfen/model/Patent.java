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
    private String applicationNum;  //申请号
    private String publicationNum;  //公布号
    private String authorizationNum;    //授权号
    private LocalDateTime applicationDate;
    private LocalDateTime publicationDate;
    private LocalDateTime authorizationDate;
    private String mainClaim;
    private String abstractText; //摘要 避免冲突，重命名为 `abstractContent`
    private String applicants;
    private String inventorsId;
    private String fieldOfResearch;
    private Integer views;
}
