package com.ruanfen.Docs;

import com.ruanfen.enums.FieldOfResearch;
import com.ruanfen.model.Article;
import com.ruanfen.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class ArticleDoc {

    private Long articleId; // 对应 long 类型
    private String articleName; // 对应 text 类型，使用 ik_smart 分词器
    private String researcherName; // 对应 keyword 类型
    private String source; // 对应 text 类型，使用 ik_smart 分词器
    private Date publishTime; // 对应 date 类型，不会被索引
    private String fieldOfResearch; // 对应 keyword 类型
    private String all; // 对应 text 类型，使用 ik_max_word 分词器


    public ArticleDoc(Article article){
        articleId = Long.valueOf(article.getArticleId());
        articleName = article.getArticleName();
        researcherName = article.getResearcherName();
        source = article.getSource();
        publishTime = article.getPublishTime();
        fieldOfResearch = article.getFieldOfResearch();

        this.all = " ";
    }

    public void setResearcherName(String researcherName){
        if(researcherName == null){
            this.researcherName = "DEFAULT";
            return;
        }
        this.researcherName = researcherName;
    }

    public static String getFieldType(String field) {
        Map<String, String> fieldTypeMap = new HashMap() {
            {
                put("articleName", "text");
                put("researcherName", "text");
                put("source", "text");
                put("fieldOfResearch", "text");
                put("all", "text");
            }
        };
        return fieldTypeMap.getOrDefault(field, null);
    }

    public ArticleDoc(){}

}
