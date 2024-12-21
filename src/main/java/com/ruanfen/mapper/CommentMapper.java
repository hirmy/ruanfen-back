package com.ruanfen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruanfen.model.Comment;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper extends BaseMapper<Comment> {
    Comment findCommentByAchieve(@Param("achievementType") int achieveType, @Param("achievementId") int achievementId);
}