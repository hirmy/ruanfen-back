package com.ruanfen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruanfen.mapper.CommentMapper;
import com.ruanfen.model.Comment;
import com.ruanfen.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    public void addComment(Comment comment){
        commentMapper.insert(comment);
    }
    public Comment findCommentByAchieve(int achievementType,int achievementId){
        return commentMapper.findCommentByAchieve(achievementType,achievementId);
    }
}