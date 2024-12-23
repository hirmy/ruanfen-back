package com.ruanfen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruanfen.mapper.ArticleMapper;
import com.ruanfen.model.Article;
import com.ruanfen.service.ArticleService;
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

    @Override
    public List<Article> searchArticlesByUrls(List<String> urls) {
        // 使用 LambdaQueryWrapper 构建查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Article::getUrl, urls);  // 查询 urls 列表中的 url 对应的文章

        return articleMapper.selectList(queryWrapper);  // 查询并返回结果
    }

}
