package com.ruanfen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruanfen.model.Comment;
import com.ruanfen.model.Project;

public interface CommentService extends IService<Comment> {
    void addComment(Comment comment);
    Comment findCommentByAchieve(int achievementType,int achievementId);
}