package com.ruanfen;

import com.ruanfen.model.Article;
import com.ruanfen.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AppTest {
    @Autowired
    private ArticleService articleService;


    @Test
    public void testArticle(){
        List<Article> articleList = articleService.list();
        System.out.println(articleList);
        return;
    }

}
