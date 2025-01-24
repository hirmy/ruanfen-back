package com.ruanfen;

import com.alibaba.fastjson.JSON;
import com.ruanfen.Docs.ResearcherDoc;
import com.ruanfen.mapper.ArticleMapper;
import com.ruanfen.model.Article;
import com.ruanfen.model.Result;
import com.ruanfen.service.ArticleService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class AppTest {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleMapper articleMapper;
    private RestHighLevelClient client;


    @Test
    public void testArticle() throws IOException {
        Article article = new Article();
        article.setArticleName("new2");

        articleMapper.insert(article);
        long id = article.getArticleId();
        System.out.println("id :" + id);
        return;
    }

}
