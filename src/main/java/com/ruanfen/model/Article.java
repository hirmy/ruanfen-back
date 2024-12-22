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

    @TableField("article_name")
    private String articleName;

    private String doi;

    private String abstractText; // 注意，字段名可能需要映射，避免与 Java 关键字冲突

    private String keywords;

    @TableField("researcher_id")
    private Integer researcherId;

    private String researcherUrl;

    private String fieldOfResearch;

    @TableField("publish_time")
    private LocalDateTime publishTime;

    @TableField("category_num")
    private String categoryNum;

    private Integer pages;

    private Integer views;

    private String source;

    private String url;

    @TableField("references_ids")
    private String referencesIds; // 存储引用文献 ID 串

}
