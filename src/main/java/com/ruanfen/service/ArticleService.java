package com.ruanfen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruanfen.model.Article;
import com.ruanfen.model.Researcher;

import java.util.List;

public interface ArticleService extends IService<Article> {
    void addArticle(Article article);
    List<Article> searchArticles(String articleName, String keywords, String fieldOfResearch, String publishTimeFrom, String publishTimeTo);


    List<Article> searchArticlesByUrls(List<String> urls);
}
