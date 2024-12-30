package com.ruanfen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruanfen.mapper.ArticleMapper;
import com.ruanfen.mapper.ResearcherMapper;
import com.ruanfen.model.Article;
import com.ruanfen.model.Researcher;
import com.ruanfen.service.ArticleService;
import com.ruanfen.service.ResearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;


    @Autowired
    private ResearcherService researcherService;

    public void addArticle(Article article) {
        articleMapper.insert(article);
        long newId = article.getArticleId();
        String researcherName = article.getResearcherName();

        Researcher researcher = researcherService.getOne(new QueryWrapper<Researcher>().eq("name", researcherName));

        if (researcher != null) {
            String articleIds = researcher.getArticleIds();

            if (articleIds == null) {
                articleIds = "";
            }

            articleIds = articleIds + (articleIds.isEmpty() ? "" : ",") + newId;

            researcher.setArticleIds(articleIds);
            researcherService.updateById(researcher);
        } else {
            // 如果找不到对应的 Researcher，可以根据需求处理
            throw new RuntimeException("Researcher not found with name: " + researcherName);
        }

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
