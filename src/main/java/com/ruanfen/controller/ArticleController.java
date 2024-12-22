package com.ruanfen.controller;

import com.ruanfen.model.Article;
import com.ruanfen.model.Researcher;
import com.ruanfen.model.Result;
import com.ruanfen.model.User;
import com.ruanfen.service.ArticleService;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

=======
import com.ruanfen.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
>>>>>>> zmk
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

<<<<<<< HEAD
    @PostMapping("/add")
    public Result addArticle(@RequestBody Article article) {
        articleService.addArticle(article);
=======
    @Autowired
    private SearchService searchService;

    @PostMapping("/add")
    public Result addArticle(@RequestBody Article article) throws IOException {
        articleService.addArticle(article);

        searchService.addArticleDoc(article);
>>>>>>> zmk
        return Result.success("文章添加成功");
    }

    @DeleteMapping("/remove")
<<<<<<< HEAD
    public Result removeArticle(@RequestParam("articleId") int articleId) {
=======
    public Result removeArticle(@RequestParam("articleId") int articleId) throws IOException {
>>>>>>> zmk
        if (articleService.getById(articleId) == null) {
            return Result.error("文章不存在，无法删除");
        }

        boolean isRemoved = articleService.removeById(articleId);
        if (isRemoved) {
<<<<<<< HEAD
=======
            searchService.deleteArticleDoc(articleId);

>>>>>>> zmk
            return Result.success("文章删除成功");
        }
        return Result.error("文章删除失败，请重试");
    }

    @PutMapping("/update")
<<<<<<< HEAD
    public Result updateArticle(@RequestParam("articleId") int articleId, @RequestBody Article article) {
=======
    public Result updateArticle(@RequestParam("articleId") int articleId, @RequestBody Article article) throws IOException {
>>>>>>> zmk
        if (articleService.getById(articleId) == null) {
            return Result.error("文章不存在，无法更新");
        }
        boolean isUpdated = articleService.updateById(article);
        if (isUpdated) {
<<<<<<< HEAD
=======
            searchService.updateArticleDoc(articleId, article);

>>>>>>> zmk
            return Result.success();
        }
        return Result.error("文章更新失败，请重试");
    }

    @GetMapping("/find")
    public Result<Article> findArticle(@RequestParam("articleId") int articleId) {
        Article article = articleService.getById(articleId);
        if (article == null) {
            return Result.error("文章不存在");
        }
        return Result.success(article);
    }

    @GetMapping("allArticles")
    public Result<List<Article>> findAllArticles() {
        List<Article> articleList = articleService.list();
        return Result.success(articleList);
    }

    @GetMapping("/search")
    public Result<List<Article>> searchArticle(@RequestParam(value = "article_name", required = false) String articleName,
                                               @RequestParam(value = "keywords", required = false) String keywords,
                                               @RequestParam(value = "field_of_research", required = false) String fieldOfResearch,
                                               @RequestParam(value = "publish_time_from", required = false) String publishTimeFrom,
                                               @RequestParam(value = "publish_time_to", required = false) String publishTimeTo) {
        List<Article> articles = articleService.searchArticles(articleName, keywords, fieldOfResearch, publishTimeFrom, publishTimeTo);
        return Result.success(articles);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> zmk
