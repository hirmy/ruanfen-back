package com.ruanfen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruanfen.mapper.CommentMapper;
import com.ruanfen.model.Comment;
import com.ruanfen.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    public void addComment(Comment comment){
        commentMapper.insert(comment);
    }
    public List<Comment> findCommentByAchieve(int achievementType, int achievementId){
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("achievement_type", achievementType)
                .eq("achievement_id", achievementId);

        return commentMapper.selectList(queryWrapper);
    }
}