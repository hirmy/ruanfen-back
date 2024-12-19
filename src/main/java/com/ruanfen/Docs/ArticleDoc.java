package com.ruanfen.Docs;

import com.ruanfen.enums.FieldOfResearch;
import com.ruanfen.model.Article;
import com.ruanfen.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Data
public class ArticleDoc {

    private Long articleId; // 对应 long 类型
    private String articleName; // 对应 text 类型，使用 ik_smart 分词器
    private String researcherName; // 对应 keyword 类型
    private String source; // 对应 text 类型，使用 ik_smart 分词器
    private LocalDateTime publishTime; // 对应 date 类型，不会被索引
    private FieldOfResearch fieldOfResearch; // 对应 keyword 类型
    private String all; // 对应 text 类型，使用 ik_max_word 分词器


    public ArticleDoc(Article article){
        articleId = Long.valueOf(article.getArticleId());
        articleName = article.getArticleName();
        //researcherName
        researcherName = null;
        source = article.getSource();
        publishTime = article.getPublishTime();
        fieldOfResearch = article.getFieldOfResearch();

    }

}
