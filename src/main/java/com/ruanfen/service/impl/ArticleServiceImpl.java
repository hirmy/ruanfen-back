package com.ruanfen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruanfen.mapper.ArticleMapper;
import com.ruanfen.mapper.ResearcherMapper;
import com.ruanfen.mapper.UserMapper;
import com.ruanfen.model.Article;
import com.ruanfen.model.User;
import com.ruanfen.service.ArticleService;
import com.ruanfen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    public void addArticle(Article article) {
        articleMapper.insert(article);
    }
    public List<Article> searchArticles(String articleName, String keywords, String fieldOfResearch, String publishTimeFrom, String publishTimeTo) {
        return articleMapper.searchArticles(articleName, keywords, fieldOfResearch, publishTimeFrom, publishTimeTo);
    }
}
