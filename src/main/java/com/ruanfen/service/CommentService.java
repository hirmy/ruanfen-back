package com.ruanfen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruanfen.model.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {
    void addComment(Comment comment);
    List<Comment> findCommentByAchieve(int achievementType, int achievementId);
}