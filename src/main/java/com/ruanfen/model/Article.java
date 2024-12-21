package com.ruanfen.model;


import com.baomidou.mybatisplus.annotation.*;
import com.ruanfen.enums.FieldOfResearch;
import lombok.Data;
import org.apache.ibatis.type.EnumTypeHandler;

import java.time.LocalDateTime;

@Data
@TableName("article")
public class Article {
    @TableId(value = "article_id", type = IdType.AUTO) // 主键，自增
    private Integer articleId;

    private String articleName;

    private String doi;

    private String abstractText; // 注意，字段名可能需要映射，避免与 Java 关键字冲突

    private String keywords;

    private Integer researcherId;

    @EnumValue
    private FieldOfResearch fieldOfResearch;

    private LocalDateTime publishTime;

    private String categoryNum;

    private Integer pages;

    private Integer views;

    private String source;

    @TableField("references_ids")
    private String referencesIds; // 存储引用文献 ID 串

}
