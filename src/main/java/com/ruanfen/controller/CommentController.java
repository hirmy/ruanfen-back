package com.ruanfen.controller;

import com.ruanfen.model.Article;
import com.ruanfen.model.Comment;
import com.ruanfen.model.Result;
import com.ruanfen.service.ArticleService;
import com.ruanfen.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public Result addComment(@RequestParam int userId, @RequestParam int achievementType, @RequestParam int achievementId, @RequestParam String commentContent) {
        // 构建评论对象
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setAchievementType(achievementType);
        comment.setAchievementId(achievementId);
        comment.setCommentContent(commentContent);
        comment.setCommentTime(new Date()); // 设置当前时间

        boolean saved = commentService.save(comment);

        if (saved) {
            return Result.success("评论添加成功");
        } else {
            return Result.error("评论添加失败");
        }
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
    public Result<List<Comment>> findCommentByAchieve(@RequestParam("achievementType") int achievementType, @RequestParam("achievementId") int achievementId) {
        List<Comment> comments = commentService.findCommentByAchieve(achievementType, achievementId);
        if (comments == null) {
            return Result.error("评论不存在");
        }
        return Result.success(comments);
    }

}
