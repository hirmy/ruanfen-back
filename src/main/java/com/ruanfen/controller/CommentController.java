package com.ruanfen.controller;

import com.ruanfen.model.Article;
import com.ruanfen.model.Comment;
import com.ruanfen.model.Result;
import com.ruanfen.service.ArticleService;
import com.ruanfen.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public Result addComment(@RequestBody Comment comment) {
        commentService.addComment(comment);
        return Result.success("评论添加成功");
    }

    @DeleteMapping("/remove")
    public Result removeComment(@RequestParam("commentId") int commentId) {
        if (commentService.getById(commentId) == null) {
            return Result.error("评论不存在，无法删除");
        }

        boolean isRemoved = commentService.removeById(commentId);
        if (isRemoved) {
            return Result.success("评论删除成功");
        }
        return Result.error("评论删除失败，请重试");
    }

    @GetMapping("/findById")
    public Result<Comment> findCommentById(@RequestParam("commentId") int commentId) {
        Comment comment = commentService.getById(commentId);
        if (comment == null) {
            return Result.error("评论不存在");
        }
        return Result.success(comment);
    }

    @GetMapping("/find")
    public Result<Comment> findCommentByAchieve(@RequestParam("achievementType") int achievementType, @RequestParam("achievementId") int achievementId) {
        Comment comment = commentService.findCommentByAchieve(achievementType, achievementId);
        if (comment == null) {
            return Result.error("评论不存在");
        }
        return Result.success(comment);
    }

}
