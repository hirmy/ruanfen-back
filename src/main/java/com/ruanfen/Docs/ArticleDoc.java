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
    private String fieldOfResearch; // 对应 keyword 类型
    private String all; // 对应 text 类型，使用 ik_max_word 分词器


    public ArticleDoc(Article article){
        articleId = Long.valueOf(article.getArticleId());
        articleName = article.getArticleName();
        //researcherName
        researcherName = null;
        source = article.getSource();
        publishTime = article.getPublishTime();
        fieldOfResearch = article.getFieldOfResearch().getName();

        // 将需要合并到 `all` 字段的字段值拼接
        StringBuilder allFields = new StringBuilder();
        allFields.append(articleName).append(" ")
                .append(source)
                .append(fieldOfResearch);
        this.all = allFields.toString(); // 将拼接的字段值赋值给 `all`
    }

    public void setResearcherName(String researcherName){
        this.researcherName = researcherName;
        this.all = this.all + researcherName;
    }

    public ArticleDoc(){}

}
